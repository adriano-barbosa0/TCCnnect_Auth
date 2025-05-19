package com.api.TCCnnect.repository;

import com.api.TCCnnect.model.Follow;
import com.api.TCCnnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follow, UUID> {
    void findByFollowerId(UUID followerId);

    List<Follow> findByFollower(User user);
}
