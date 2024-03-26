package com.example.communityoftravellers2.service;

import com.example.communityoftravellers2.dto.UserProfileDTO;
import com.example.communityoftravellers2.model.UserProfile;
import org.springframework.http.ResponseEntity;

public interface UserProfileService {
    ResponseEntity<UserProfile> createUserProfile(UserProfileDTO userProfileDTO);

    ResponseEntity<UserProfile> updateUserProfile(UserProfileDTO userProfileDTO);
}
