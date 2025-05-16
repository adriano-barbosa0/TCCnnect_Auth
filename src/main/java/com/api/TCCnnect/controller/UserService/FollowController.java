package com.api.TCCnnect.controller.UserService;

import com.api.TCCnnect.dto.FollowRequest;
import com.api.TCCnnect.services.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/following")
    public ResponseEntity<?> WhoIamFollowing(@RequestParam String userId) {

        var flw = followService.getFollowing(userId);
        return ResponseEntity.ok(flw);
    }

    @PostMapping
    public ResponseEntity<?> followUser(@RequestBody FollowRequest followRequest) {
        followService.followUser(followRequest);
        return ResponseEntity.ok("User followed successfully");
    }
}
