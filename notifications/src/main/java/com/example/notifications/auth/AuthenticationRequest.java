package com.example.notifications.auth;

import com.example.notifications.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private  String email ;
    private String password ;
    private Role role;
    private  boolean enabled ;
}
