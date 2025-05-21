package com.api.TCCnnect.controller.User;

import com.api.TCCnnect.dto.FollowRequestDTO;
import com.api.TCCnnect.dto.FollowResponseDTO;
import com.api.TCCnnect.dto.FollowingResponseDTO;
import com.api.TCCnnect.services.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @Operation(summary = "Obtém a lista de usuários seguidos", description = "Retorna a lista de usuários que o usuário especificado está seguindo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários seguidos retornada com sucesso", content = @Content(schema = @Schema(implementation = FollowingResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetro de ID do usuário inválido", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/following")
    public ResponseEntity<List<FollowingResponseDTO>> getFollowing(@RequestParam String userId) {
        List<FollowingResponseDTO> following = followService.getFollowing(userId);
        return ResponseEntity.ok(following);
    }

    @Operation(summary = "Segue um usuário", description = "Permite que o usuário autenticado siga outro usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário seguido com sucesso", content = @Content(schema = @Schema(implementation = FollowResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<FollowResponseDTO> followUser(@RequestBody FollowRequestDTO followRequestDTO) {
        FollowResponseDTO response = followService.followUser(followRequestDTO);
        return ResponseEntity.ok(response);
    }
}
