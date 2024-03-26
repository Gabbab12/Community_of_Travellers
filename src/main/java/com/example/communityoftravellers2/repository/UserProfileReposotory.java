package com.example.communityoftravellers2.repository;

import com.example.communityoftravellers2.model.UserProfile;
import com.example.communityoftravellers2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileReposotory extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser(User user);

}
