package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.dto.enums.UserRole;
import com.api.TCCnnect.model.Usuario;
import com.api.TCCnnect.repository.UsuarioRepository;
import com.api.TCCnnect.services.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    @Override
    public Usuario findById(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void updateUser(Usuario usuario) {

        Usuario existingUser = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(usuario.getName());
        existingUser.setBio(usuario.getBio());
        existingUser.setAvatar_url(usuario.getAvatar_url());

        usuarioRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Usuario usuario) {
        Usuario existingUser = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        usuarioRepository.delete(existingUser);
    }

}
