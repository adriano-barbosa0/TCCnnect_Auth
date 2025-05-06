package com.api.TCCnnect.controller;

import com.api.TCCnnect.model.Usuario;
import com.api.TCCnnect.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario) {
        Usuario savedUser = userService.saveUser(usuario);
        return ResponseEntity.ok(savedUser);
    }

}

