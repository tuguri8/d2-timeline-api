package com.timeline.api.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum UserRole {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}