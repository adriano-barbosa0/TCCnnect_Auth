package com.api.TCCnnect.controller.UserService;

import com.api.TCCnnect.dto.UserProfileDto;
import com.api.TCCnnect.model.User;
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

        User userProfile = userService.findById(id);

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
        User user = userService.findById(userId);

        user.setName(userProfileDtoReq.name() != null ? userProfileDtoReq.name() : user.getName());
        user.setBio(userProfileDtoReq.bio() != null ? userProfileDtoReq.bio() : user.getBio());
        user.setAvatar_url(userProfileDtoReq.avatarUrl() != null ? userProfileDtoReq.avatarUrl() : user.getAvatar_url());

        userService.updateUser(user);

        UserProfileDto userProfileDto = new UserProfileDto(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getBio(),
                user.getAvatar_url()
        );

        return ResponseEntity.ok(userProfileDto);
    }

    @DeleteMapping("/delete/me")
    public ResponseEntity<Void> deleteUserProfile(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        UUID userId = tokenService.extractUserId(jwt);
        User user = userService.findById(userId);

        userService.deleteUser(user);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserProfileDto>> searchUserProfile(@RequestParam String login) {
        List<User> users = userService.findByNameStartingWith(login);
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
