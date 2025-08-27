package com.api.TCCnnect.services;

import com.api.TCCnnect.dto.FollowRequestDTO;
import com.api.TCCnnect.dto.FollowResponseDTO;
import com.api.TCCnnect.dto.FollowingResponseDTO;

import java.util.List;


public interface FollowService {

    FollowResponseDTO followUser(FollowRequestDTO followRequestDTO);

    List<FollowingResponseDTO> getFollowing(String userId);
}
