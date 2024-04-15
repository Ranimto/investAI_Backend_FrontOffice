package com.example.notifications.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityDto {
    private Long id;
    private LocalDateTime timestamp;
    private String description;
    private  Long userId ;
}
