package com.example.notifications.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    INVESTOR_READ("investor:read"),
    INVESTOR_UPDATE("investor:update"),
    INVESTOR_CREATE("investor:create"),
    INVESTOR_DELETE("investor:delete");


    @Getter
    private final String permission;
}
