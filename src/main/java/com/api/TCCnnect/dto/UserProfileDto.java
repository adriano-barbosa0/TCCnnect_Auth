package com.api.TCCnnect.dto;

import com.api.TCCnnect.model.Usuario;

import java.util.UUID;

public record UserProfileDto(UUID id, String login,String name, String bio, String avatarUrl) {


}
