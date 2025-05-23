package com.api.TCCnnect.config;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerDocsConfig {

    @Bean
    public OpenApiCustomizer controllerCustomiser() {
        return openApi -> {
            Paths paths = openApi.getPaths();

            // Documentação do AuthController
            paths.addPathItem("/auth/signup", new PathItem().post(createUserOperation()));
            paths.addPathItem("/auth/me", new PathItem().get(getMyProfileOperation()));

            // Documentação do TokenController
            paths.addPathItem("/auth/login", new PathItem().post(loginOperation()));

            // Documentação do FollowController
            paths.addPathItem("/follow/following", new PathItem().get(getFollowingOperation()));
            paths.addPathItem("/follow", new PathItem().post(followUserOperation()));

            // Documentação do UserProfileController
            paths.addPathItem("/users/{id}", new PathItem().get(getUserProfileOperation()));
            paths.addPathItem("/users/me", new PathItem().put(updateUserProfileOperation()));
            paths.addPathItem("/users/delete/me", new PathItem().delete(deleteUserProfileOperation()));
            paths.addPathItem("/users/search", new PathItem().get(searchUserProfileOperation()));
        };
    }

    private Operation createUserOperation() {
        return new Operation()
                .summary("Cria um novo usuário")
                .description("Endpoint para registrar um novo usuário na aplicação.")
                .addTagsItem("Auth")
                .requestBody(new RequestBody()
                        .description("Dados necessários para criar um novo usuário")
                        .required(true)
                        .content(new Content().addMediaType("application/json",
                                new MediaType().schema(new Schema<>().$ref("#/components/schemas/UserSignUpDTO")))))
                .responses(new ApiResponses()
                        .addApiResponse("200", new ApiResponse().description("Usuário criado com sucesso"))
                        .addApiResponse("400", new ApiResponse()
                                .description("Erro de validação")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().type("string"))))));
    }

    private Operation getMyProfileOperation() {
        return new Operation()
                .summary("Obtém o perfil do usuário logado")
                .description("Retorna os dados do perfil do usuário autenticado com base no token JWT.")
                .addTagsItem("Auth")
                .addParametersItem(new Parameter()
                        .name("Authorization")
                        .in("header")
                        .required(true)
                        .schema(new Schema<>().type("string")))
                .responses(new ApiResponses()
                        .addApiResponse("200", new ApiResponse()
                                .description("Perfil do usuário retornado com sucesso")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/UserProfileDTO")))))
                        .addApiResponse("401", new ApiResponse()
                                .description("Token inválido ou não fornecido")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().type("string"))))));
    }

    private Operation loginOperation() {
        return new Operation()
                .summary("Realiza o login do usuário")
                .description("Endpoint para autenticar um usuário e gerar um token JWT.")
                .addTagsItem("Token JWT")
                .requestBody(new RequestBody()
                        .description("Dados necessários para autenticação")
                        .required(true)
                        .content(new Content().addMediaType("application/json",
                                new MediaType().schema(new Schema<>().$ref("#/components/schemas/DadosAutenticacaoDTO")))))
                .responses(new ApiResponses()
                        .addApiResponse("200", new ApiResponse()
                                .description("Login realizado com sucesso")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/DadosTokenJwtDTO")))))
                        .addApiResponse("400", new ApiResponse()
                                .description("Erro de validação ou credenciais inválidas")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().type("string"))))));
    }

    private Operation getFollowingOperation() {
        return new Operation()
                .summary("Obtém a lista de usuários seguidos")
                .description("Retorna a lista de usuários que o usuário especificado está seguindo.")
                .addTagsItem("Follow")
                .addParametersItem(new Parameter()
                        .name("userId")
                        .in("query")
                        .required(true)
                        .schema(new Schema<>().type("string")))
                .responses(new ApiResponses()
                        .addApiResponse("200", new ApiResponse()
                                .description("Lista de usuários seguidos retornada com sucesso")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/FollowingResponseDTO")))))
                        .addApiResponse("400", new ApiResponse()
                                .description("Parâmetro de ID do usuário inválido")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().type("string"))))));
    }

    private Operation followUserOperation() {
        return new Operation()
                .summary("Segue um usuário")
                .description("Permite que o usuário autenticado siga outro usuário.")
                .addTagsItem("Follow")
                .requestBody(new RequestBody()
                        .description("Dados necessários para seguir um usuário")
                        .required(true)
                        .content(new Content().addMediaType("application/json",
                                new MediaType().schema(new Schema<>().$ref("#/components/schemas/FollowRequestDTO")))))
                .responses(new ApiResponses()
                        .addApiResponse("200", new ApiResponse()
                                .description("Usuário seguido com sucesso")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/FollowResponseDTO")))))
                        .addApiResponse("400", new ApiResponse()
                                .description("Dados de requisição inválidos")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().type("string"))))));
    }

    private Operation getUserProfileOperation() {
        return new Operation()
                .summary("Obtém o perfil de um usuário")
                .description("Retorna os dados do perfil de um usuário com base no ID fornecido.")
                .addTagsItem("UserProfile")
                .addParametersItem(new Parameter()
                        .name("id")
                        .in("path")
                        .required(true)
                        .schema(new Schema<>().type("string")))
                .responses(new ApiResponses()
                        .addApiResponse("200", new ApiResponse()
                                .description("Perfil do usuário retornado com sucesso")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/UserProfileDTO")))))
                        .addApiResponse("404", new ApiResponse()
                                .description("Usuário não encontrado")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().type("string"))))));
    }

    private Operation updateUserProfileOperation() {
        return new Operation()
                .summary("Atualiza o perfil do usuário logado")
                .description("Permite que o usuário autenticado atualize seu perfil.")
                .addTagsItem("UserProfile")
                .addParametersItem(new Parameter()
                        .name("Authorization")
                        .in("header")
                        .required(true)
                        .schema(new Schema<>().type("string")))
                .requestBody(new RequestBody()
                        .description("Dados para atualizar o perfil do usuário")
                        .required(true)
                        .content(new Content().addMediaType("application/json",
                                new MediaType().schema(new Schema<>().$ref("#/components/schemas/UserProfileDTO")))))
                .responses(new ApiResponses()
                        .addApiResponse("200", new ApiResponse()
                                .description("Perfil atualizado com sucesso")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/UserProfileDTO")))))
                        .addApiResponse("401", new ApiResponse()
                                .description("Token inválido ou não fornecido")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().type("string"))))));
    }

    private Operation deleteUserProfileOperation() {
        return new Operation()
                .summary("Exclui o perfil do usuário logado")
                .description("Permite que o usuário autenticado exclua seu perfil.")
                .addTagsItem("UserProfile")
                .addParametersItem(new Parameter()
                        .name("Authorization")
                        .in("header")
                        .required(true)
                        .schema(new Schema<>().type("string")))
                .responses(new ApiResponses()
                        .addApiResponse("204", new ApiResponse().description("Perfil excluído com sucesso"))
                        .addApiResponse("401", new ApiResponse()
                                .description("Token inválido ou não fornecido")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().type("string"))))));
    }

    private Operation searchUserProfileOperation() {
        return new Operation()
                .summary("Busca perfis de usuários")
                .description("Busca perfis de usuários cujo login começa com o valor fornecido.")
                .addTagsItem("UserProfile")
                .addParametersItem(new Parameter()
                        .name("login")
                        .in("query")
                        .required(true)
                        .schema(new Schema<>().type("string")))
                .responses(new ApiResponses()
                        .addApiResponse("200", new ApiResponse()
                                .description("Perfis encontrados com sucesso")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/UserProfileDTO")))))
                        .addApiResponse("400", new ApiResponse()
                                .description("Parâmetro de busca inválido")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<>().type("string"))))));
    }
}
