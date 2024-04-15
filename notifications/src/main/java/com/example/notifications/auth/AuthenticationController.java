package com.example.notifications.auth;

import com.example.notifications.Repository.AppUserRepo;

import com.example.notifications.impl.UserServiceImpl;
import com.example.notifications.models.AppUser;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserServiceImpl userService;
    private final AppUserRepo userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request, HttpServletRequest http, Model model) throws MessagingException, UnsupportedEncodingException {

        return ResponseEntity.ok(authenticationService.register(request, getSiteURL(http), model));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        Optional<AppUser> user = userRepository.findByEmail(request.getEmail());
        if ( user.get().isEnabled()) {
            return ResponseEntity.ok(authenticationService.authenticate(request));}
       else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            System.out.println("verify_success");
            return "verify_success";
        } else {
            System.out.println("verify_fail");
            return "verify_fail";
        }
    }
}
