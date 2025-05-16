package com.api.TCCnnect.controller.UserService;

import com.api.TCCnnect.dto.UserProfileDto;
import com.api.TCCnnect.model.Usuario;
import com.api.TCCnnect.services.TokenService;
import com.api.TCCnnect.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserProfileController {

    private  final TokenService tokenService;
    private final UserService userService;

    public UserProfileController( TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable UUID id) {

        Usuario userProfile = userService.findById(id);

        UserProfileDto userProfileDto = new UserProfileDto(
                userProfile.getId(),
                userProfile.getLogin(),
                userProfile.getName(),
                userProfile.getBio(),
                userProfile.getAvatar_url()
        );

        return ResponseEntity.ok(userProfileDto);
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileDto> updateUserProfile(@RequestHeader("Authorization") String token,
                                                              @RequestBody UserProfileDto userProfileDtoReq) {
        String jwt = token.replace("Bearer ", "");
        UUID userId = tokenService.extractUserId(jwt);
        Usuario usuario = userService.findById(userId);

        usuario.setName(userProfileDtoReq.name() != null ? userProfileDtoReq.name() : usuario.getName());
        usuario.setBio(userProfileDtoReq.bio() != null ? userProfileDtoReq.bio() : usuario.getBio());
        usuario.setAvatar_url(userProfileDtoReq.avatarUrl() != null ? userProfileDtoReq.avatarUrl() : usuario.getAvatar_url());

        userService.updateUser(usuario);

        UserProfileDto userProfileDto = new UserProfileDto(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getName(),
                usuario.getBio(),
                usuario.getAvatar_url()
        );

        return ResponseEntity.ok(userProfileDto);
    }

    @DeleteMapping("/delete/me")
    public ResponseEntity<Void> deleteUserProfile(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        UUID userId = tokenService.extractUserId(jwt);
        Usuario usuario = userService.findById(userId);

        userService.deleteUser(usuario);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserProfileDto>> searchUserProfile(@RequestParam String login) {
        List<Usuario> users = userService.findByNameStartingWith(login);
        List<UserProfileDto> userProfiles = users.stream()
                .map(user -> new UserProfileDto(
                        user.getId(),
                        user.getLogin(),
                        user.getName(),
                        user.getBio(),
                        user.getAvatar_url()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userProfiles);
    }



}
