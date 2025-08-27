package com.api.TCCnnect.dto;

import java.util.UUID;

public record FollowRequestDTO(UUID followerId, UUID followedId) {
}
