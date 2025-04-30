package com.api.TCCnnect.dto.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    User("user");
    private String role;
    UserRole(String role) {this.role = role;}
}
