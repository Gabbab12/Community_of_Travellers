package com.example.communityoftravellers2.service;

import com.example.communityoftravellers2.dto.PostDTO;
import com.example.communityoftravellers2.enums.HistoricalPeriod;
import com.example.communityoftravellers2.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PostService {
    ResponseEntity<Post> createPost(Long userId, PostDTO postDTO);

    Page<Post> getAllPosts(Pageable pageable);

    Post getPostById(Long postId);

    ResponseEntity<Post> editPost(Long postId, PostDTO postDTO);

    ResponseEntity<String> deletePost(Long postId, Long userId);

    ResponseEntity<Post> upvotePost(Long postId);

    ResponseEntity<Post> downVotePost(Long postId);

    List<Post> searchPost(String keyword, String topic, HistoricalPeriod historicalPeriod);
}
