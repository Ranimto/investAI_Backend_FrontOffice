package com.example.notifications.Services;

import com.example.notifications.Dto.UserDto;
import com.example.notifications.models.AppUser;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface AppUserService {
    public UserDto addUser(UserDto userDto);
    public AppUser addUserr(AppUser user);
    public Optional<UserDto> findUserById(Long id);
    public UserDto findUserByVerificationCode(String code);
    public List<UserDto> findAllUser();
    public UserDto UpdateUser(UserDto userDto);
    public void DeleteById(Long id);
    public void sendVerificationEmail(AppUser user, String siteURL)throws MessagingException, UnsupportedEncodingException;
    public boolean verify(String verificationCode);
    public UserDto findUserByEmail(String email);
}
