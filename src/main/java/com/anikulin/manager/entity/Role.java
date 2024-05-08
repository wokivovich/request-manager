package com.anikulin.manager.entity;

public enum Role {
    USER("ROLE_USER"), OPERATOR("ROLE_OPERATOR"), ADMIN("ROLE_ADMIN");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
