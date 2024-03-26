package com.example.communityoftravellers2.controller;

import com.example.communityoftravellers2.dto.UserProfileDTO;
import com.example.communityoftravellers2.model.UserProfile;
import com.example.communityoftravellers2.service.serviceImpl.UserProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-profile")
public class UserProfileController {
    private final UserProfileServiceImpl userProfileService;

    @PostMapping("/create-user-profile")
    public ResponseEntity<UserProfile> createUserProfile(@RequestBody UserProfileDTO userProfileDTO){
        return userProfileService.createUserProfile(userProfileDTO);
    }

    @PutMapping("/update-users-profile")
    public ResponseEntity<UserProfile> updateUserProfile(@RequestBody UserProfileDTO userProfileDTO){
        return userProfileService.updateUserProfile(userProfileDTO);
    }
}
