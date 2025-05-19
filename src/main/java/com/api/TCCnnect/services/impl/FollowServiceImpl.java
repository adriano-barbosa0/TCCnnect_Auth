package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.dto.FollowRequest;
import com.api.TCCnnect.dto.FollowResponse;
import com.api.TCCnnect.dto.FollowingResponse;
import com.api.TCCnnect.model.Follow;
import com.api.TCCnnect.model.User;
import com.api.TCCnnect.repository.FollowRepository;
import com.api.TCCnnect.repository.UsuarioRepository;
import com.api.TCCnnect.services.FollowService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FollowServiceImpl implements FollowService {


    private final FollowRepository followRepository;
    private final UsuarioRepository usuarioRepository;

    public FollowServiceImpl(FollowRepository followRepository, UsuarioRepository usuarioRepository) {
        this.followRepository = followRepository;
        this.usuarioRepository = usuarioRepository;



}


    @Override
    public FollowResponse followUser(FollowRequest followRequest) {
        User follower = usuarioRepository.findById(followRequest.followerId())
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        User followed = usuarioRepository.findById(followRequest.followedId())
                .orElseThrow(() -> new RuntimeException("Followed user not found"));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowed(followed);

        followRepository.save(follow);

        return new FollowResponse(follower.getLogin(), followed.getLogin());
    };

    @Override
    public List<FollowingResponse> getFollowing(String userId) {
        User user = usuarioRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Follow> follows = followRepository.findByFollower(user);

        return follows.stream()
                .map(follow -> new FollowingResponse(
                        follow.getFollowed().getId(),
                        follow.getFollowed().getName(),
                        follow.getFollowed().getLogin(),
                        follow.getFollowed().getAvatar_url()
                ))
                .toList();
    }
}
