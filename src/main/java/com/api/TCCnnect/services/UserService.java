package com.api.TCCnnect.services;

import com.api.TCCnnect.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User saveUser(User user);

    User findById(UUID id);

    void updateUser(User user);

    void deleteUser(User user);

    List<User> findByNameStartingWith(String namePrefix);
}

