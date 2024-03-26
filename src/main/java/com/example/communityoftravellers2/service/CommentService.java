package com.example.communityoftravellers2.service;

import com.example.communityoftravellers2.dto.CommentDTO;
import com.example.communityoftravellers2.model.Comment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    ResponseEntity<Comment> createComments(Long postId, CommentDTO commentDTO);

    List<Comment> getAllPostComment(Long postId);
}
