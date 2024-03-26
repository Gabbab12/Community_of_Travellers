package com.example.communityoftravellers2.controller;

import com.example.communityoftravellers2.dto.PostDTO;
import com.example.communityoftravellers2.enums.HistoricalPeriod;
import com.example.communityoftravellers2.model.Post;
import com.example.communityoftravellers2.service.serviceImpl.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostServiceImpl postService;

    @PostMapping("/create-post/{userId}")
    public ResponseEntity<Post> createPosts(@PathVariable Long userId, @RequestBody PostDTO postDTO){
        return postService.createPost(userId, postDTO);
    }

    @GetMapping("/get-all-posts")
    public ResponseEntity<Page<Post>> getPostsByUserId(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<Post> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/update-post/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody PostDTO postDTO){
        return postService.editPost(postId, postDTO);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @RequestParam Long userId) {
        return postService.deletePost(postId, userId);
    }
    @PostMapping("/upvote/{postId}")
    public ResponseEntity<Post> upvotePost(@PathVariable Long postId) {
       return postService.upvotePost(postId);
    }

    @PostMapping("/downVote/{postId}")
    public ResponseEntity<Post> downvotePost(@PathVariable Long postId) {
        return postService.downVotePost(postId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPosts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) HistoricalPeriod historicalPeriod) {

        List<Post> posts = postService.searchPost(keyword, topic, historicalPeriod);
        return ResponseEntity.ok(posts);
    }
}
