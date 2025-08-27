package com.api.TCCnnect.controller.Auth;

import com.api.TCCnnect.dto.UserProfileDTO;
import com.api.TCCnnect.dto.UserSignUpDTO;
import com.api.TCCnnect.model.User;
import com.api.TCCnnect.services.TokenService;
import com.api.TCCnnect.services.UserService;

import jakarta.validation.Valid;
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
    public ResponseEntity<String> createUser(@RequestBody @Valid UserSignUpDTO user) {
        userService.saveUser(user);
        return ResponseEntity.ok("usuario criado com sucesso");
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getMyProfile(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        UUID userId = tokenService.extractUserId(jwt);
        User user = userService.findById(userId);
        UserProfileDTO userProfile = new UserProfileDTO(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getBio(),
                user.getAvatar_url()
        );
        return ResponseEntity.ok(userProfile);
    }

}

