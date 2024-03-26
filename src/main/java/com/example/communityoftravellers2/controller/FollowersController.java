package com.example.communityoftravellers2.controller;

import com.example.communityoftravellers2.model.Follower;
import com.example.communityoftravellers2.service.serviceImpl.FollowerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/follow")
public class FollowersController {
    private final FollowerServiceImpl followerService;

    @PostMapping("/users/follow/{followingUserId}")
    public ResponseEntity<String> followUser(@RequestParam Long userId, @PathVariable Long followingUserId) {
        return followerService.followUser(userId, followingUserId);
    }

    @GetMapping("/users/{userId}/following")
    public List<Follower> getUsersFollowing(@PathVariable Long userId) {
        return followerService.getUserFollowers(userId);
    }
}
