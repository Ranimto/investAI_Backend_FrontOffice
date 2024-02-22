package com.example.notifications.Dto;

import com.example.notifications.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private double phone;
    private String title;
    private String bio ;
    private String imageUrl;
    private String enabled;
    private LocalDateTime createdAt;
    private Role role ;
    private String verificationCode ;

}
