package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.dto.FollowRequestDTO;
import com.api.TCCnnect.dto.FollowResponseDTO;
import com.api.TCCnnect.dto.FollowingResponseDTO;
import com.api.TCCnnect.model.Follow;
import com.api.TCCnnect.model.User;
import com.api.TCCnnect.repository.FollowRepository;
import com.api.TCCnnect.repository.UserRepository;
import com.api.TCCnnect.services.FollowService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FollowServiceImpl implements FollowService {


    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowServiceImpl(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;



}


    @Override
    public FollowResponseDTO followUser(FollowRequestDTO followRequestDTO) {
        User follower = userRepository.findById(followRequestDTO.followerId())
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        User followed = userRepository.findById(followRequestDTO.followedId())
                .orElseThrow(() -> new RuntimeException("Followed user not found"));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowed(followed);

        followRepository.save(follow);

        return new FollowResponseDTO(follower.getLogin(), followed.getLogin());
    };

    @Override
    public List<FollowingResponseDTO> getFollowing(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Follow> follows = followRepository.findByFollower(user);

        return follows.stream()
                .map(follow -> new FollowingResponseDTO(
                        follow.getFollowed().getId(),
                        follow.getFollowed().getName(),
                        follow.getFollowed().getLogin(),
                        follow.getFollowed().getAvatar_url()
                ))
                .toList();
    }
}
