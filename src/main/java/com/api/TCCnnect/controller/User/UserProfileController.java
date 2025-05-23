package com.api.TCCnnect.controller.User;

import com.api.TCCnnect.dto.UserProfileDTO;
import com.api.TCCnnect.model.User;
import com.api.TCCnnect.services.TokenService;
import com.api.TCCnnect.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable UUID id) {

        User userProfile = userService.findById(id);

        UserProfileDTO userProfileDto = new UserProfileDTO(
                userProfile.getId(),
                userProfile.getLogin(),
                userProfile.getName(),
                userProfile.getBio(),
                userProfile.getAvatar_url()
        );

        return ResponseEntity.ok(userProfileDto);
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileDTO> updateUserProfile(@RequestHeader("Authorization") String token,
                                                            @RequestBody UserProfileDTO userProfileDTOReq) {
        String jwt = token.replace("Bearer ", "");
        UUID userId = tokenService.extractUserId(jwt);
        User user = userService.findById(userId);

        user.setName(userProfileDTOReq.name() != null ? userProfileDTOReq.name() : user.getName());
        user.setBio(userProfileDTOReq.bio() != null ? userProfileDTOReq.bio() : user.getBio());
        user.setAvatar_url(userProfileDTOReq.avatar_url() != null ? userProfileDTOReq.avatar_url() : user.getAvatar_url());

        userService.updateUser(user);

        UserProfileDTO userProfileDto = new UserProfileDTO(
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
    public ResponseEntity<List<UserProfileDTO>> searchUserProfile(@RequestParam String login) {
        List<User> users = userService.findByNameStartingWith(login);
        List<UserProfileDTO> userProfiles = users.stream()
                .map(user -> new UserProfileDTO(
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
