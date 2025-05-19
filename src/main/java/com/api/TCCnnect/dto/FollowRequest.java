package com.api.TCCnnect.dto;

import java.util.UUID;

public record FollowRequest(UUID followerId, UUID followedId) {
}
