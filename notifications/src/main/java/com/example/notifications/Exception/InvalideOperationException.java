package com.example.notifications.Exception;

import lombok.Data;

@Data
public class InvalideOperationException extends RuntimeException{
    private ErrorCode errorCode;

    public InvalideOperationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }}

