package com.example.notifications.auth;

import com.example.notifications.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private  String firstname ;
    private  String lastname ;
    private String email;
    private  String password;
    private double phone;
    private Role role;
    private  boolean enabled ;
    private String verificationCode ;
}
