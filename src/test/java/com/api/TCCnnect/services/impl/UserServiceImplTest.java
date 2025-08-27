package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.dto.UserSignUpDTO;
import com.api.TCCnnect.dto.enums.UserRole;
import com.api.TCCnnect.model.User;
import com.api.TCCnnect.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUserSuccessfullySavesNewUser() {
        UserSignUpDTO newUserDto = new UserSignUpDTO("newUser", "password");
        User newUser = new User();
        newUser.setLogin("newUser");
        newUser.setPassword("encodedPassword");
        newUser.setRole(UserRole.User);

        when(userRepository.findByLogin("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User savedUser = userService.saveUser(newUserDto);

        assertNotNull(savedUser);
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(UserRole.User, savedUser.getRole());
    }

    @Test
    void saveUserThrowsExceptionWhenUserAlreadyExists() {
        User existingUser = new User();
        existingUser.setLogin("existingUser");

        when(userRepository.findByLogin("existingUser")).thenReturn(Optional.of(existingUser));

        UserSignUpDTO newUserDto = new UserSignUpDTO("existingUser", "password");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.saveUser(newUserDto));
        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    void findByIdReturnsUserWhenFound() {
        UUID userId = UUID.randomUUID();
        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        User foundUser = userService.findById(userId);

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());

    }

    @Test
    void findByIdThrowsExceptionWhenUserNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.findById(userId));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void updateUserSuccessfullyUpdatesExistingUser() {
        UUID userId = UUID.randomUUID();
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("Old Name");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName("New Name");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        userService.updateUser(updatedUser);

        assertEquals("New Name", existingUser.getName());
    }

    @Test
    void updateUserThrowsExceptionWhenUserNotFound() {
        User userToUpdate = new User();
        userToUpdate.setId(UUID.randomUUID());

        when(userRepository.findById(userToUpdate.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.updateUser(userToUpdate));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void deleteUserSuccessfullyDeletesExistingUser() {
        UUID userId = UUID.randomUUID();
        User existingUser = new User();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.deleteUser(existingUser);

        verify(userRepository).delete(existingUser);
    }

    @Test
    void deleteUserThrowsExceptionWhenUserNotFound() {
        User userToDelete = new User();
        userToDelete.setId(UUID.randomUUID());

        when(userRepository.findById(userToDelete.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(userToDelete));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void findByNameStartingWithReturnsUsersWhenFound() {
        String namePrefix = "user";
        User user1 = new User();
        user1.setLogin("user1");
        User user2 = new User();
        user2.setLogin("user2");

        when(userRepository.findByLoginStartingWithIgnoreCase(namePrefix)).thenReturn(List.of(user1, user2));

        List<User> result = userService.findByNameStartingWith(namePrefix);

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
    }

    @Test
    void findByNameStartingWithReturnsEmptyListWhenNoUsersFound() {
        String namePrefix = "nonexistent";

        when(userRepository.findByLoginStartingWithIgnoreCase(namePrefix)).thenReturn(List.of());

        List<User> result = userService.findByNameStartingWith(namePrefix);

        assertTrue(result.isEmpty());
    }
}
