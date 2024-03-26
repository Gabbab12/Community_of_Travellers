package com.example.communityoftravellers2.service.serviceImpl;

import com.example.communityoftravellers2.dto.UserProfileDTO;
import com.example.communityoftravellers2.exception.UserAlreadyExistException;
import com.example.communityoftravellers2.model.UserProfile;
import com.example.communityoftravellers2.model.User;
import com.example.communityoftravellers2.repository.UserProfileReposotory;
import com.example.communityoftravellers2.repository.UserRepository;
import com.example.communityoftravellers2.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    private final UserRepository userRepository;
    private final UserProfileReposotory userProfileReposotory;

    @Override
    public ResponseEntity<UserProfile> createUserProfile(UserProfileDTO userProfileDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<UserProfile> optionalUserProfile = userProfileReposotory.findByUser(user);
        if (optionalUserProfile.isPresent()){
            throw new UserAlreadyExistException("user profile already exist", HttpStatus.BAD_REQUEST);
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setPhoneNumber(userProfileDTO.getPhoneNumber());
        userProfile.setAddress(userProfileDTO.getAddress());
        userProfile.setBiography(userProfileDTO.getBiography());
        userProfile.setOccupation(userProfileDTO.getOccupation());
        userProfile.setFavoriteQoute(userProfileDTO.getFavoriteQuote());
        userProfile.setExpertise(userProfileDTO.getExpertise());
        userProfile.setUser(user);

        userProfileReposotory.save(userProfile);

        return ResponseEntity.status(HttpStatus.CREATED).body(userProfile);
    }
    @Override
    public ResponseEntity<UserProfile> updateUserProfile(UserProfileDTO userProfileDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User authenticatedUser = (User) authentication.getPrincipal();

        Optional<UserProfile> optionalUserProfile = userProfileReposotory.findByUser(authenticatedUser);
        if (optionalUserProfile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        UserProfile userProfile = optionalUserProfile.get();
        userProfile.setPhoneNumber(userProfileDTO.getPhoneNumber());
        userProfile.setBiography(userProfileDTO.getBiography());
        userProfile.setAddress(userProfileDTO.getAddress());
        userProfile.setFavoriteQoute(userProfileDTO.getFavoriteQuote());
        userProfile.setOccupation(userProfileDTO.getOccupation());
        userProfile.setExpertise(userProfileDTO.getExpertise());

        log.info(String.valueOf(userProfile));
        userProfileReposotory.save(userProfile);

        return ResponseEntity.status(HttpStatus.OK).body(userProfile);
    }
}
