package com.api.TCCnnect.dto;

import java.util.UUID;

public record FollowingResponseDTO(UUID userId, String name, String login, String avatarUrl) {
}
