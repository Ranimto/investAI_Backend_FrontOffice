package com.example.notifications.Exception;

import lombok.Data;

import java.util.List;

@Data
public class InvalideEntityException extends RuntimeException{

    private ErrorCode errorCode;
    private List<String> errors;

    public InvalideEntityException(String message, ErrorCode errorCode, List<String> errors) {
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;

    }
}
