package com.example.notifications.impl;

import com.example.notifications.Dto.UserDto;
import com.example.notifications.Exception.ApiException;
import com.example.notifications.Repository.AppUserRepo;
import com.example.notifications.Repository.TokenRepository;
import com.example.notifications.Services.AppUserService;
import com.example.notifications.models.AppUser;
import com.example.notifications.models.Role;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.notifications.Exception.ErrorCode.INVESTMENT_NOT_FOUND;
import static com.example.notifications.Exception.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements AppUserService {

    private final AppUserRepo userRepo;
    private final ModelMapper modelMapper; // to convert class User to UserDto
    private final TokenRepository tokenRepository;
    @Autowired
    private JavaMailSender mailSender;

    public UserDto addUser(UserDto userDto) {
        AppUser user = modelMapper.map(userDto, AppUser.class);
        if (userRepo.countByEmail(user.getEmail().trim().toLowerCase()) > 0)
            throw new ApiException(USER_NOT_FOUND.getMessage());
        AppUser savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    //it will be used in the register method to add just user with different emails
    public AppUser addUserr(AppUser user) {
        if (userRepo.countByEmail(user.getEmail().trim().toLowerCase()) > 0) {
            throw new ApiException(INVESTMENT_NOT_FOUND.getMessage());
        }
        AppUser savedUser = userRepo.save(user);
        return savedUser;
    }


    //converting an optional<User> to optional<UserDto> is not directly supported by ModelMapper.
    public Optional<UserDto> findUserById(Long id) {
        if (id == 0) {
            log.error("l'id est null");
        }
        Optional<AppUser> user = userRepo.findById(id);
        return Optional.ofNullable(user.map(u -> modelMapper.map(u, UserDto.class)).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND.getMessage() +""+ USER_NOT_FOUND.getCode())));
    }

    public UserDto findUserByVerificationCode(String code) {
        AppUser user = userRepo.findByVerificationCode(code);
        return modelMapper.map(user, UserDto.class);
    }

    public List<UserDto> findAllUser() {
        List<AppUser> users = userRepo.findAll();
        return users.stream()
                .map(u -> modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto UpdateUser(UserDto userDto) {
        AppUser user = userRepo.findById(userDto.getId()).orElse(null);
        if (user != null) {

            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setEmail(userDto.getEmail());
            user.setProfession(userDto.getTitle());
            user.setAddress(userDto.getAddress());
            user.setBio(userDto.getBio());
            user.setCreatedAt(userDto.getCreatedAt());
            user.setPhone(userDto.getPhone());
            user.setImageUrl(userDto.getImageUrl());
            user.setRole(userDto.getRole());

            AppUser updatedUser = userRepo.save(user);
            return modelMapper.map(updatedUser, UserDto.class);
        } else {
            return null;
        }
    }

    //delete user's token then user by id
    public void DeleteById(Long id) {
        if (id.equals(0)) log.error("UserId is null");
        else
            tokenRepository.deleteAllTokenByUserId(id);
        userRepo.deleteById(id);
    }


    public void sendVerificationEmail(AppUser user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "toumiranim13@gmail.com";
        String senderName = "InvestAI team";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "InvestAI.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstname() + " " + user.getLastname());
        String verifyURL = siteURL + "/auth/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    public boolean verify(String verificationCode) {

        AppUser user = userRepo.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepo.save(user);

            return true;
        }
    }


    public UserDto findUserByEmail(String email) {
        Optional<AppUser> user = userRepo.findByEmail(email);
        return modelMapper.map(user, UserDto.class);
    }

    public boolean isInvestor(Long id) {
        Optional<AppUser> user =userRepo.findById(id);
        return user.get().getRole()== Role.INVESTOR ;
    }
}



