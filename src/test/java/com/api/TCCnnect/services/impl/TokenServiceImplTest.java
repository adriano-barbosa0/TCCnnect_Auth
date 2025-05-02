package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.model.Usuario;
import com.auth0.jwt.JWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceImplTest {

    private TokenServiceImpl tokenService;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        tokenService = new TokenServiceImpl();
        ReflectionTestUtils.setField(tokenService, "secret", "12345678");
        usuario = new Usuario("1", "userLogin@email.com", "userPassword");
    }

    @Test
    void deveGerarTokenValido() {
        Usuario usuario = new Usuario("1", "userLogin@email.com", "userPassword");
        String token = tokenService.gerarToken(usuario);
        assertNotNull(token);
    }

    @Test
    void naoDeveGerarTokenComUsuarioNulo() {
        assertThrows(RuntimeException.class, () -> tokenService.gerarToken(null));
    }

    @Test
    void deveRetornarSubjectDoToken() {
        String token = tokenService.gerarToken(usuario);
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
        String token = tokenService.gerarToken(usuario);
        String id = JWT.decode(token).getClaim("Id").asString();
        assertEquals("1", id);
    }

    @Test
    void naoDeveRetornarIdDeTokenInvalido() {
        String tokenInvalido = "tokenInvalido";
        assertThrows(RuntimeException.class, () -> JWT.decode(tokenInvalido).getClaim("Id").asString());
    }
}
