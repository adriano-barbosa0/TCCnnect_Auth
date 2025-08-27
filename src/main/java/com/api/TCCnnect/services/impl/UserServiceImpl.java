package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.dto.UserSignUpDTO;
import com.api.TCCnnect.dto.enums.UserRole;
import com.api.TCCnnect.model.User;
import com.api.TCCnnect.repository.UserRepository;
import com.api.TCCnnect.services.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(UserSignUpDTO user) {
        this.userRepository.findByLogin(user.login())
                .ifPresent(u -> {
                    throw new RuntimeException("User already exists");
                });
        var encodedPassword = passwordEncoder.encode(user.password());

        User newUser = new User();
        newUser.setLogin(user.login());
        newUser.setPassword(encodedPassword);
        newUser.setRole(UserRole.User);

        return userRepository.save(newUser);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void updateUser(User user) {

        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(user.getName());
        existingUser.setBio(user.getBio());
        existingUser.setAvatar_url(user.getAvatar_url());

        userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(existingUser);
    }

    @Override
    public List<User> findByNameStartingWith(String namePrefix) {
        return userRepository.findByLoginStartingWithIgnoreCase(namePrefix);

    }

}
