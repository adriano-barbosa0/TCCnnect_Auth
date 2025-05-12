package com.api.TCCnnect.dto;

import java.util.UUID;

public record UserProfileDto(UUID id, String username,String name, String bio, String avatarUrl) {
}
