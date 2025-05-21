package com.api.TCCnnect.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TCCnnect API")
                        .version("1.0.0")
                        .description("Documentação da API do TCCnnect").contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Equipe TCCnnect")
                                .email("contato@tccnnect.com"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT"))
                )
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("jwt_auth"))
                .schemaRequirement("jwt_auth", createSecurityScheme());
    }

    private SecurityScheme createSecurityScheme(){
        return new SecurityScheme().name("jwt_auth").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
    }
}
