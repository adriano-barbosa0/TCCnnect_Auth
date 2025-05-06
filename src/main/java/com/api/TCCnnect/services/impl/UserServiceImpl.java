package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.dto.enums.UserRole;
import com.api.TCCnnect.model.Usuario;
import com.api.TCCnnect.repository.UsuarioRepository;
import com.api.TCCnnect.services.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario saveUser(Usuario usuario) {
        this.usuarioRepository.findByLogin(usuario.getLogin())
                .ifPresent(u -> {
                    throw new RuntimeException("User already exists");
                });
        var encodedPassword = passwordEncoder.encode(usuario.getPassword());

        usuario.setPassword(encodedPassword);
        usuario.setRole(UserRole.User);

        return usuarioRepository.save(usuario);
    }

}
