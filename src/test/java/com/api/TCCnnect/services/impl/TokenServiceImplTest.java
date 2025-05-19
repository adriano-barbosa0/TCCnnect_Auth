package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.model.User;
import com.auth0.jwt.JWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceImplTest {

    private TokenServiceImpl tokenService;
    private User user;

    @BeforeEach
    void setUp() {
        tokenService = new TokenServiceImpl();
        ReflectionTestUtils.setField(tokenService, "secret", "12345678");
        user = new User((UUID.fromString("63e954c3-191c-4231-8382-bf5e900e3828")), "userLogin@email.com", "userPassword", null, null, null, null, null, null);
    }

    @Test
    void deveGerarTokenValido() {
        String token = tokenService.gerarToken(user);
        String id = JWT.decode(token).getClaim("Id").asString();
        assertEquals("63e954c3-191c-4231-8382-bf5e900e3828", id);
    }

    @Test
    void naoDeveGerarTokenComUsuarioNulo() {
        assertThrows(RuntimeException.class, () -> tokenService.gerarToken(null));
    }

    @Test
    void deveRetornarSubjectDoToken() {
        String token = tokenService.gerarToken(user);
        String subject = tokenService.getSubject(token);
        assertEquals("userLogin@email.com", subject);
    }

    @Test
    void naoDeveRetornarSubjectDeTokenInvalido() {
        String tokenInvalido = "tokenInvalido";
        assertThrows(RuntimeException.class, () -> tokenService.getSubject(tokenInvalido));
    }

    @Test
    void deveConterIdCorretoNoToken() {
        String token = tokenService.gerarToken(user);
        String id = JWT.decode(token).getClaim("Id").asString();
        assertEquals("63e954c3-191c-4231-8382-bf5e900e3828", id);
    }

    @Test
    void naoDeveRetornarIdDeTokenInvalido() {
        String tokenInvalido = "tokenInvalido";
        assertThrows(RuntimeException.class, () -> JWT.decode(tokenInvalido).getClaim("Id").asString());
    }
}
