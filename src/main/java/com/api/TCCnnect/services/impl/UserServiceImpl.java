package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.dto.enums.UserRole;
import com.api.TCCnnect.model.User;
import com.api.TCCnnect.repository.UsuarioRepository;
import com.api.TCCnnect.services.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public User saveUser(User user) {
        this.usuarioRepository.findByLogin(user.getLogin())
                .ifPresent(u -> {
                    throw new RuntimeException("User already exists");
                });
        var encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        user.setRole(UserRole.User);

        return usuarioRepository.save(user);
    }

    @Override
    public User findById(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void updateUser(User user) {

        User existingUser = usuarioRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(user.getName());
        existingUser.setBio(user.getBio());
        existingUser.setAvatar_url(user.getAvatar_url());

        usuarioRepository.save(existingUser);
    }

    @Override
    public void deleteUser(User user) {
        User existingUser = usuarioRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        usuarioRepository.delete(existingUser);
    }

    @Override
    public List<User> findByNameStartingWith(String namePrefix) {
        return usuarioRepository.findByLoginStartingWithIgnoreCase(namePrefix);

    }

}
