package com.api.TCCnnect.controller.Auth;

import com.api.TCCnnect.dto.UserProfileDTO;
import com.api.TCCnnect.dto.UserSignUpDTO;
import com.api.TCCnnect.model.User;
import com.api.TCCnnect.services.TokenService;
import com.api.TCCnnect.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Cria um novo usuário", description = "Endpoint para registrar um novo usuário na aplicação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserSignUpDTO user) {
        userService.saveUser(user);
        return ResponseEntity.ok("usuario criado com sucesso");
    }

    @Operation(summary = "Obtém o perfil do usuário logado", description = "Retorna os dados do perfil do usuário autenticado com base no token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil do usuário retornado com sucesso", content = @Content(schema = @Schema(implementation = UserProfileDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido ou não fornecido", content = @Content(schema = @Schema(implementation = String.class)))
    })
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

