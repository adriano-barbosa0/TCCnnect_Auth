package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.dto.enums.UserRole;
import com.api.TCCnnect.model.Usuario;
import com.api.TCCnnect.repository.UsuarioRepository;
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

class UserServiceImplTest {
    @Mock
    private UsuarioRepository usuarioRepository;

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
        Usuario newUser = new Usuario();
        newUser.setLogin("newUser");
        newUser.setPassword("password");

        when(usuarioRepository.findByLogin("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(usuarioRepository.save(newUser)).thenReturn(newUser);

        Usuario savedUser = userService.saveUser(newUser);

        assertNotNull(savedUser);
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(UserRole.User, savedUser.getRole());
    }

    @Test
    void saveUserThrowsExceptionWhenUserAlreadyExists() {
        Usuario existingUser = new Usuario();
        existingUser.setLogin("existingUser");

        when(usuarioRepository.findByLogin("existingUser")).thenReturn(Optional.of(existingUser));

        Usuario newUser = new Usuario();
        newUser.setLogin("existingUser");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.saveUser(newUser));
        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    void findByIdReturnsUserWhenFound() {
        UUID userId = UUID.randomUUID();
        Usuario existingUser = new Usuario();
        existingUser.setId(userId);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        Usuario foundUser = userService.findById(userId);

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());

    }

    @Test
    void findByIdThrowsExceptionWhenUserNotFound() {
        UUID userId = UUID.randomUUID();

        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.findById(userId));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void updateUserSuccessfullyUpdatesExistingUser() {
        UUID userId = UUID.randomUUID();
        Usuario existingUser = new Usuario();
        existingUser.setId(userId);
        existingUser.setName("Old Name");

        Usuario updatedUser = new Usuario();
        updatedUser.setId(userId);
        updatedUser.setName("New Name");

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(usuarioRepository.save(existingUser)).thenReturn(existingUser);
        userService.updateUser(updatedUser);

        assertEquals("New Name", existingUser.getName());
    }

    @Test
    void updateUserThrowsExceptionWhenUserNotFound() {
        Usuario userToUpdate = new Usuario();
        userToUpdate.setId(UUID.randomUUID());

        when(usuarioRepository.findById(userToUpdate.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.updateUser(userToUpdate));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void deleteUserSuccessfullyDeletesExistingUser() {
        UUID userId = UUID.randomUUID();
        Usuario existingUser = new Usuario();
        existingUser.setId(userId);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.deleteUser(existingUser);

        verify(usuarioRepository).delete(existingUser);
    }

    @Test
    void deleteUserThrowsExceptionWhenUserNotFound() {
        Usuario userToDelete = new Usuario();
        userToDelete.setId(UUID.randomUUID());

        when(usuarioRepository.findById(userToDelete.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(userToDelete));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void findByNameStartingWithReturnsUsersWhenFound() {
        String namePrefix = "user";
        Usuario user1 = new Usuario();
        user1.setLogin("user1");
        Usuario user2 = new Usuario();
        user2.setLogin("user2");

        when(usuarioRepository.findByLoginStartingWithIgnoreCase(namePrefix)).thenReturn(List.of(user1, user2));

        List<Usuario> result = userService.findByNameStartingWith(namePrefix);

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
    }

    @Test
    void findByNameStartingWithReturnsEmptyListWhenNoUsersFound() {
        String namePrefix = "nonexistent";

        when(usuarioRepository.findByLoginStartingWithIgnoreCase(namePrefix)).thenReturn(List.of());

        List<Usuario> result = userService.findByNameStartingWith(namePrefix);

        assertTrue(result.isEmpty());
    }
}
