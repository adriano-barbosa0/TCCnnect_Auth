package com.api.TCCnnect.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DtoDocsConfig {

    @Bean
    public OpenApiCustomizer dtoCustomiser() {
        return openApi -> {
            Components components = openApi.getComponents();

            // Adiciona o esquema do UserSignUpDTO
            components.addSchemas("UserSignUpDTO", new Schema<>()
                    .type("object")
                    .addProperties("login", new Schema<>()
                            .type("string")
                            .format("email")
                            .description("E-mail do usuário que será usado como login")
                            .example("usuario@exemplo.com"))
                    .addProperties("password", new Schema<>()
                            .type("string")
                            .description("Senha do usuário")
                            .example("senha123")));

            // Adiciona o esquema do UserProfileDTO
            components.addSchemas("UserProfileDTO", new Schema<>()
                    .type("object")
                    .addProperties("id", new Schema<>()
                            .type("string")
                            .format("uuid")
                            .description("ID único do usuário"))
                    .addProperties("login", new Schema<>()
                            .type("string")
                            .description("Login do usuário"))
                    .addProperties("name", new Schema<>()
                            .type("string")
                            .description("Nome do usuário"))
                    .addProperties("bio", new Schema<>()
                            .type("string")
                            .description("Biografia do usuário"))
                    .addProperties("avatar_url", new Schema<>()
                            .type("string")
                            .description("URL do avatar do usuário")));

            // Adiciona o esquema do DadosAutenticacaoDTO
            components.addSchemas("DadosAutenticacaoDTO", new Schema<>()
                    .type("object")
                    .addProperties("login", new Schema<>()
                            .type("string")
                            .description("Login do usuário")
                            .example("usuario@exemplo.com"))
                    .addProperties("password", new Schema<>()
                            .type("string")
                            .description("Senha do usuário")
                            .example("senha123")));

            // Adiciona o esquema do FollowingResponseDTO
            components.addSchemas("FollowingResponseDTO", new Schema<>()
                    .type("object")
                    .addProperties("userId", new Schema<>()
                            .type("string")
                            .format("uuid")
                            .description("ID do usuário seguido"))
                    .addProperties("name", new Schema<>()
                            .type("string")
                            .description("Nome do usuário seguido"))
                    .addProperties("login", new Schema<>()
                            .type("string")
                            .description("Login do usuário seguido"))
                    .addProperties("avatarUrl", new Schema<>()
                            .type("string")
                            .description("URL do avatar do usuário seguido")));

            // Adiciona o esquema do FollowRequestDTO
            components.addSchemas("FollowRequestDTO", new Schema<>()
                    .type("object")
                    .addProperties("followerId", new Schema<>()
                            .type("string")
                            .format("uuid")
                            .description("ID do usuário que está seguindo"))
                    .addProperties("followedId", new Schema<>()
                            .type("string")
                            .format("uuid")
                            .description("ID do usuário que está sendo seguido")));

            // Adiciona o esquema do FollowResponseDTO
            components.addSchemas("FollowResponseDTO", new Schema<>()
                    .type("object")
                    .addProperties("followerName", new Schema<>()
                            .type("string")
                            .description("Nome do usuário que está seguindo"))
                    .addProperties("followedName", new Schema<>()
                            .type("string")
                            .description("Nome do usuário que está sendo seguido")));
        };
    }
}