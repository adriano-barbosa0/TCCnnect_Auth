package com.api.TCCnnect.controller.UserService;

import com.api.TCCnnect.dto.FollowRequest;
import com.api.TCCnnect.dto.FollowResponse;
import com.api.TCCnnect.dto.FollowingResponse;
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
    public ResponseEntity<List<FollowingResponse>> getFollowing(@RequestParam String userId) {
        List<FollowingResponse> following = followService.getFollowing(userId);
        return ResponseEntity.ok(following);
    }

    @PostMapping
    public ResponseEntity<FollowResponse> followUser(@RequestBody FollowRequest followRequest) {
        FollowResponse response = followService.followUser(followRequest);
        return ResponseEntity.ok(response);
    }
}
