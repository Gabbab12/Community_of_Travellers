package com.example.communityoftravellers2.repository;

import com.example.communityoftravellers2.model.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowersRepository extends JpaRepository<Follower, Long> {
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Follower f WHERE f.user.id = :userId AND f.followingUser.id = :followingUserId")
    boolean existsByUserIdAndFollowingUserId(@Param("userId") Long userId, @Param("followingUserId") Long followingUserId);
    List<Follower> findFollowingUsersByUserId(Long userId);

}
