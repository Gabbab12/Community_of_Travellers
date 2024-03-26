package com.example.communityoftravellers2.controller;

import com.example.communityoftravellers2.dto.CommentDTO;
import com.example.communityoftravellers2.model.Comment;
import com.example.communityoftravellers2.service.serviceImpl.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/comments")
public class CommentsController {
    private final CommentServiceImpl commentService;

    @PostMapping("/create-comments/{postId}")
    public ResponseEntity<Comment> createComments(@PathVariable Long postId, @RequestBody CommentDTO commentDTO){
        return commentService.createComments(postId, commentDTO);
    }

    @GetMapping("/get-all-comments/{postId}")
    public ResponseEntity<List<Comment>> getCommentsForPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getAllPostComment(postId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }
}
