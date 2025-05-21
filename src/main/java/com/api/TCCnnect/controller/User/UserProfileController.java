package com.api.TCCnnect.controller.User;

import com.api.TCCnnect.dto.UserProfileDTO;
import com.api.TCCnnect.model.User;
import com.api.TCCnnect.services.TokenService;
import com.api.TCCnnect.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Obtém o perfil de um usuário", description = "Retorna os dados do perfil de um usuário com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil do usuário retornado com sucesso", content = @Content(schema = @Schema(implementation = UserProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
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

    @Operation(summary = "Atualiza o perfil do usuário logado", description = "Permite que o usuário autenticado atualize seu perfil.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso", content = @Content(schema = @Schema(implementation = UserProfileDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido ou não fornecido", content = @Content(schema = @Schema(implementation = String.class)))
    })
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

    @Operation(summary = "Exclui o perfil do usuário logado", description = "Permite que o usuário autenticado exclua seu perfil.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfil excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou não fornecido", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/delete/me")
    public ResponseEntity<Void> deleteUserProfile(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        UUID userId = tokenService.extractUserId(jwt);
        User user = userService.findById(userId);

        userService.deleteUser(user);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca perfis de usuários", description = "Busca perfis de usuários cujo login começa com o valor fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfis encontrados com sucesso", content = @Content(schema = @Schema(implementation = UserProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetro de busca inválido", content = @Content(schema = @Schema(implementation = String.class)))
    })
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
