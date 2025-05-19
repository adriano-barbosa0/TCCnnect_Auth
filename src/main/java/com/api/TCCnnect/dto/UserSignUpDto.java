package com.api.TCCnnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record UserSignUpDto(@NotBlank
                            @Email(message = "O login deve ser um e-mail válido")
                            String login,
                            @NotBlank
                            String password) {
}
