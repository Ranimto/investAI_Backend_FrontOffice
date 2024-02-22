package com.example.notifications.Exception;

import lombok.Data;

@Data
public class EntityNotFoundException extends RuntimeException{

    private ErrorCode errorCode;

    public EntityNotFoundException(String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
