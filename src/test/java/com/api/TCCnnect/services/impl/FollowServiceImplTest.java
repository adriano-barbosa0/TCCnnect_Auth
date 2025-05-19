package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.dto.FollowRequest;
import com.api.TCCnnect.dto.FollowResponse;
import com.api.TCCnnect.dto.FollowingResponse;
import com.api.TCCnnect.model.Follow;
import com.api.TCCnnect.model.User;
import com.api.TCCnnect.repository.FollowRepository;
import com.api.TCCnnect.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FollowServiceImplTest {
    private FollowRepository followRepository;
    private UsuarioRepository usuarioRepository;
    private FollowServiceImpl followService;

    @BeforeEach
    void setUp() {
        followRepository = mock(FollowRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        followService = new FollowServiceImpl(followRepository, usuarioRepository);
    }

    @Test
    void deveSeguirUsuarioComSucesso() {
        UUID followerId = UUID.randomUUID();
        UUID followedId = UUID.randomUUID();

        User follower = new User(followerId, "followerLogin", "password", "Follower Name", "bio", "avatar_url", null, null, null);
        User followed = new User(followedId, "followedLogin", "password", "Followed Name", "bio", "avatar_url", null, null, null);

        when(usuarioRepository.findById(followerId)).thenReturn(Optional.of(follower));
        when(usuarioRepository.findById(followedId)).thenReturn(Optional.of(followed));

        FollowRequest followRequest = new FollowRequest(followerId, followedId);
        FollowResponse response = followService.followUser(followRequest);

        assertNotNull(response);
        assertEquals("followerLogin", response.followerName());
        assertEquals("followedLogin", response.followedName());
        verify(followRepository, times(1)).save(any(Follow.class));
    }

    @Test
    void deveLancarExcecaoQuandoFollowerNaoEncontrado() {
        UUID followerId = UUID.randomUUID();
        UUID followedId = UUID.randomUUID();

        when(usuarioRepository.findById(followerId)).thenReturn(Optional.empty());

        FollowRequest followRequest = new FollowRequest(followerId, followedId);

        assertThrows(RuntimeException.class, () -> followService.followUser(followRequest));
        verify(followRepository, never()).save(any(Follow.class));
    }

    @Test
    void deveLancarExcecaoQuandoFollowedNaoEncontrado() {
        UUID followerId = UUID.randomUUID();
        UUID followedId = UUID.randomUUID();

        User follower = new User(followerId, "followerLogin", "password", "Follower Name", "bio", "avatar_url", null, null, null);

        when(usuarioRepository.findById(followerId)).thenReturn(Optional.of(follower));
        when(usuarioRepository.findById(followedId)).thenReturn(Optional.empty());

        FollowRequest followRequest = new FollowRequest(followerId, followedId);

        assertThrows(RuntimeException.class, () -> followService.followUser(followRequest));
        verify(followRepository, never()).save(any(Follow.class));
    }

    @Test
    void deveRetornarListaDeSeguidores() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "userLogin", "password", "User Name", "bio", "avatar_url", null, null, null);

        Follow follow = new Follow(UUID.randomUUID(), user, new User(UUID.randomUUID(), "followedLogin", "password", "Followed Name", "bio", "avatar_url", null, null, null));

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(user));
        when(followRepository.findByFollower(user)).thenReturn(List.of(follow));

        List<FollowingResponse> following = followService.getFollowing(userId.toString());
        System.out.println(following);
        assertNotNull(following);
        assertEquals(1, following.size());
        assertEquals("Followed Name", following.get(0).name());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoBuscarSeguidores() {
        UUID userId = UUID.randomUUID();

        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> followService.getFollowing(userId.toString()));
        assertEquals("User not found", exception.getMessage());
    }
}