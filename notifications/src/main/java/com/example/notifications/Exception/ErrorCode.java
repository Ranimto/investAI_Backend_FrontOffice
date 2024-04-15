package com.example.notifications.Exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVESTMENT_NOT_FOUND(301,"investment not found"),
    INVESTMENT_NOT_VALID(302,"investment not valid"),
    INVESTMENT_ALREADY_EXISTS(303, "investment already exists"),

    COMPANY_NOT_FOUND(304,"company not found"),
    COMPANY_NOT_VALID(305, "company not valid"),
    COMPANY_ALREADY_EXISTS(306, "company already exists"),

    BANK_ACCOUNT_NOT_FOUND(307,"bank account not found" ),
    BANK_ACCOUNT_NOT_VALID(308, "bank account not valid"),
    BANK_ACCOUNT_ALREADY_EXISTS(309, "bank account already exists"),

    PROFILE_DATA_NOT_FOUND(310,"profile data not found" ),
    PROFILE_DATA_NOT_VALID(311, "profile data not valid"),
    PROFILE_DATA_ALREADY_EXISTS(312, "profile data already exists"),

    USER_NOT_FOUND(313,"user not found" ),
    EMAIL_ALREADY_EXISTS(313,"user not found" ),
    USER_NOT_VALID(314, "user not valid"),
    USER_ALREADY_EXISTS(315, "user already exists"),

    REQUEST_ACCOUNT_NOT_FOUND(316,"requestAccount not found" ),
    REQUEST_ACCOUNT_NOT_VALID(317, "requestAccount not valid"),
    REQUEST_ACCOUNT_ALREADY_EXISTS(318, "requestAccount already exists"),
    REQUEST_ACCOUNT_ID_IS_NULL(319,"requestAccount not found" );



    private int code;
    private String message ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
