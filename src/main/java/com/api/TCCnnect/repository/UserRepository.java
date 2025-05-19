package com.api.TCCnnect.repository;

import com.api.TCCnnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<UserDetails> findByLogin(String login);
    List<User> findByLoginStartingWithIgnoreCase(String namePrefix);
}
