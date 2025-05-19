package com.api.TCCnnect.services;

import com.api.TCCnnect.dto.FollowRequest;
import com.api.TCCnnect.dto.FollowResponse;
import com.api.TCCnnect.dto.FollowingResponse;

import java.util.List;


public interface FollowService {

    FollowResponse followUser(FollowRequest followRequest);

    List<FollowingResponse> getFollowing(String userId);
}
