package com.example.communityoftravellers2.service.serviceImpl;

import com.example.communityoftravellers2.exception.NoFollowerFoundException;
import com.example.communityoftravellers2.exception.UserAlreadyFollowedException;
import com.example.communityoftravellers2.exception.UserNotFoundException;
import com.example.communityoftravellers2.model.Follower;
import com.example.communityoftravellers2.model.User;
import com.example.communityoftravellers2.repository.FollowersRepository;
import com.example.communityoftravellers2.repository.UserRepository;
import com.example.communityoftravellers2.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {
    private final FollowersRepository followersRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<String> followUser(Long userId, Long followingUserId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId, HttpStatus.NOT_FOUND));

        User followingUser = userRepository.findById(followingUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + followingUserId, HttpStatus.NOT_FOUND));

        if (followersRepository.existsByUserIdAndFollowingUserId(userId, followingUserId)) {
            throw new UserAlreadyFollowedException("User with ID " + userId + " is already following user with ID " + followingUserId, HttpStatus.BAD_REQUEST);
        }
        Follower follower = new Follower();
        follower.setUser(user);
        follower.setFollowingUser(followingUser);

        followersRepository.save(follower);

        return ResponseEntity.status(HttpStatus.CREATED).body("you have successfully followed " + followingUserId);
    }
    @Override
    public List<Follower> getUserFollowers(Long userId) {
        List<Follower> followingUsers = followersRepository.findFollowingUsersByUserId(userId); if (!followingUsers.isEmpty()) {
            return followingUsers;
        } else {
            throw new NoFollowerFoundException("You do not have any follower", HttpStatus.NOT_FOUND);
        }
    }
}
