package com.example.notifications.auth;

import com.example.notifications.EmailValidator.EmailValidator;
import com.example.notifications.Repository.TokenRepository;
import com.example.notifications.Repository.AppUserRepo;
import com.example.notifications.config.JwtService;
import com.example.notifications.impl.UserServiceImpl;
import com.example.notifications.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final AppUserRepo userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private  final EmailValidator emailValidator ;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationResponse register(RegisterRequest request , String siteURL, Model model)  throws UnsupportedEncodingException, MessagingException {
        //String generatedPassword = RandomStringUtils.randomAlphanumeric(8);
        Role role = Role.valueOf(request.getRole().name().toUpperCase());
        logger.info("Role value: {}", role);
        AppUser user = new AppUser();
        switch (role) {
            case ADMIN:
                user.setRole(role.ADMIN);
                break;
            case INVESTOR:
                user.setRole(role.INVESTOR);
                break;
            default:
                // Handle unsupported roles
                throw new IllegalArgumentException("Invalid role specified.");
        }

//        //EMAIL VALIDATOR
//        boolean isValidEmail=emailValidator.test(request.getEmail());
//         if (!isValidEmail) {
//             throw new IllegalArgumentException("Email not valid");
//         }

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setEnabled(false);
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());

        //code for email verification
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);

        var savedUser = userService.addUserr(user);
        var jwtToken = jwtService.generateToken(user);
        // var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);
        //send verification code
        userService.sendVerificationEmail(user, siteURL);
        model.addAttribute("page Title", "Registration Succeeded!");
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build() ;
    }

    // in case if the username or the password is not correct an exception will be thrown
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();;
        //once i get the user I will generate the token then return the authenticationResponse

        //condition ??
        var jwtToken = jwtService.generateToken(user);
        //var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(AppUser user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

// This method takes a user as an argument, retrieves all of their valid tokens,
// marks them as expired and revoked, and then saves those changes to the database.

    private void revokeAllUserTokens(AppUser user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .token(accessToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}

