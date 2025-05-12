package com.api.TCCnnect.controller.AuthService;

import com.api.TCCnnect.dto.UserProfileDto;
import com.api.TCCnnect.model.Usuario;
import com.api.TCCnnect.services.TokenService;
import com.api.TCCnnect.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    public AuthController(UserService userService , TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody Usuario usuario) {
        Usuario savedUser = userService.saveUser(usuario);
        return ResponseEntity.ok("usuario criado com sucesso");
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMyProfile(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        UUID userId = tokenService.extractUserId(jwt);
        Usuario usuario = userService.findById(userId);
        UserProfileDto userProfile = new UserProfileDto(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getBio(),
                usuario.getAvatar_url()
        );
        return ResponseEntity.ok(userProfile);
    }

}

