package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.model.Usuario;
import com.api.TCCnnect.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticacaoServiceTest {


    private UsuarioRepository usuarioRepository;
    private AuthenticacaoService authenticacaoService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        authenticacaoService = new AuthenticacaoService(usuarioRepository);
    }

    @Test
    void deveCarregarUsuarioPorUsername() {
        Usuario usuario = new Usuario("1", "user", "password");
        when(usuarioRepository.findByLogin("user")).thenReturn(usuario);

        var result = authenticacaoService.loadUserByUsername("user");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
    }


    @Test
    void deveLancarExcecaoSeUsuarioNaoEncontrado() {
        when(usuarioRepository.findByLogin("user")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            authenticacaoService.loadUserByUsername("user");
        });
    }

}