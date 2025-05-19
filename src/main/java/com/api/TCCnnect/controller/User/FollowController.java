package com.api.TCCnnect.controller.User;

import com.api.TCCnnect.dto.FollowRequestDTO;
import com.api.TCCnnect.dto.FollowResponseDTO;
import com.api.TCCnnect.dto.FollowingResponseDTO;
import com.api.TCCnnect.services.FollowService;
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

    @GetMapping("/following")
    public ResponseEntity<List<FollowingResponseDTO>> getFollowing(@RequestParam String userId) {
        List<FollowingResponseDTO> following = followService.getFollowing(userId);
        return ResponseEntity.ok(following);
    }

    @PostMapping
    public ResponseEntity<FollowResponseDTO> followUser(@RequestBody FollowRequestDTO followRequestDTO) {
        FollowResponseDTO response = followService.followUser(followRequestDTO);
        return ResponseEntity.ok(response);
    }
}
