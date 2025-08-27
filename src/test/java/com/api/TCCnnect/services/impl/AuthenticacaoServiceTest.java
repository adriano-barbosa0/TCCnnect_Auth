package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.model.User;
import com.api.TCCnnect.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticacaoServiceTest {


    private UserRepository userRepository;
    private AuthenticacaoService authenticacaoService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        authenticacaoService = new AuthenticacaoService(userRepository);
    }

    @Test
    void deveCarregarUsuarioPorUsername() {
        User user = new User(((UUID.fromString("4702a9cd-983d-417e-9ff1-78fa71be5419"))), "user", "password" , "name", "bio", "avatar_url", null, null, null);
        when(userRepository.findByLogin("user")).thenReturn(Optional.of(user));

        var result = authenticacaoService.loadUserByUsername("user");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
    }


    @Test
    void deveLancarExcecaoSeUsuarioNaoEncontrado() {
        when(userRepository.findByLogin("user")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            authenticacaoService.loadUserByUsername("user");
        });
    }

}