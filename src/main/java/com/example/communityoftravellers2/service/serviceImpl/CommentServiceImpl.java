package com.example.communityoftravellers2.service.serviceImpl;

import com.example.communityoftravellers2.dto.CommentDTO;
import com.example.communityoftravellers2.exception.PostNotFoundException;
import com.example.communityoftravellers2.model.Comment;
import com.example.communityoftravellers2.model.Post;
import com.example.communityoftravellers2.repository.CommentRepository;
import com.example.communityoftravellers2.repository.PostRepository;
import com.example.communityoftravellers2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public ResponseEntity<Comment> createComments(Long postId, CommentDTO commentDTO){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post not found with ID: " + postId, HttpStatus.NOT_FOUND));

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setPost(post);

        commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @Override
    public List<Comment> getAllPostComment(Long postId){
        return commentRepository.findByPostId(postId);
    }
}
