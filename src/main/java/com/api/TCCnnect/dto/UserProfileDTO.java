package com.api.TCCnnect.dto;

import java.util.UUID;

public record UserProfileDTO(UUID id, String login, String name, String bio, String avatar_url) {


}
