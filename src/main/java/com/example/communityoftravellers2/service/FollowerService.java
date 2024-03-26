package com.example.communityoftravellers2.service;

import com.example.communityoftravellers2.model.Follower;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FollowerService {
    ResponseEntity<String> followUser(Long userId, Long followingUserId);

    List<Follower> getUserFollowers(Long userId);
}
