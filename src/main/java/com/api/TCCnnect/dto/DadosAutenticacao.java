package com.api.TCCnnect.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(@NotBlank(message = "login é obrigatorio") String login,
                                @NotBlank(message = "Senha é obrigatória")String password) {

}
