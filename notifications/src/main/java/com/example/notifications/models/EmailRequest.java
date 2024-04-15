package com.example.notifications.models;

import lombok.Data;

@Data
public class EmailRequest {
    private String from;
    private String to;
    private String subject;
    private String message;
}
