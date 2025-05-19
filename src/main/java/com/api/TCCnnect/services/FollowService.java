package com.api.TCCnnect.services;

import com.api.TCCnnect.dto.FollowRequest;
import com.api.TCCnnect.model.User;

import java.util.List;


public interface FollowService {

    void followUser(FollowRequest followRequest);

    List<User> getFollowing(String userId);
}
