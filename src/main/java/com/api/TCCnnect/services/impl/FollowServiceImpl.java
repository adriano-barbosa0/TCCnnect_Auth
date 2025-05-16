package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.dto.FollowRequest;
import com.api.TCCnnect.dto.UserProfileDto;
import com.api.TCCnnect.model.Follow;
import com.api.TCCnnect.model.Usuario;
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
    public void followUser(FollowRequest followRequest) {
        Usuario follower = usuarioRepository.findById(followRequest.followerId())
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        Usuario followed = usuarioRepository.findById(followRequest.followedId())
                .orElseThrow(() -> new RuntimeException("Followed user not found"));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowed(followed);

        followRepository.save(follow);
    }

    @Override
    public List<Usuario>  getFollowing(String userId) {
        Usuario usuario = usuarioRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        List<Follow> follows = followRepository.findByFollower(usuario);
        return follows.stream()
                .map(Follow::getFollowed)
                .toList();
    }
}
