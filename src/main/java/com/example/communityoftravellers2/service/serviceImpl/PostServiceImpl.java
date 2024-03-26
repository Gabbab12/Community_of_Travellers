package com.example.communityoftravellers2.service.serviceImpl;

import com.example.communityoftravellers2.dto.PostDTO;
import com.example.communityoftravellers2.enums.HistoricalPeriod;
import com.example.communityoftravellers2.exception.NoCriteriaToSearchException;
import com.example.communityoftravellers2.exception.PostNotFoundException;
import com.example.communityoftravellers2.exception.UserNotFoundException;
import com.example.communityoftravellers2.model.Post;
import com.example.communityoftravellers2.model.User;
import com.example.communityoftravellers2.repository.PostRepository;
import com.example.communityoftravellers2.repository.UserRepository;
import com.example.communityoftravellers2.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Post> createPost(Long userId, PostDTO postDTO){

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with ID: " + userId, HttpStatus.NOT_FOUND));

        Post post = new Post();
        post.setHistoricalPeriod(postDTO.getHistoricalPeriod());
        post.setTopic(postDTO.getTopic());
        post.setDescription(postDTO.getDescription());
        post.setEncounteredFigure(postDTO.getEncounteredFigure());
        post.setFuturePredictions(postDTO.getFuturePredictions());
        post.setUser(user);

        postRepository.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + postId, HttpStatus.NOT_FOUND));
    }
    @Override
    public ResponseEntity<Post> editPost(Long postId, PostDTO postDTO){
        Post existingPost = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post not found with ID: " + postId, HttpStatus.NOT_FOUND));
        existingPost.setDescription(postDTO.getDescription());
        existingPost.setTopic(postDTO.getTopic());
        existingPost.setEncounteredFigure(postDTO.getEncounteredFigure());
        existingPost.setFuturePredictions(postDTO.getFuturePredictions());
        existingPost.setHistoricalPeriod(postDTO.getHistoricalPeriod());
        postRepository.save(existingPost);

        return ResponseEntity.status(HttpStatus.OK).body(existingPost);
    }
    @Override
    public ResponseEntity<String> deletePost(Long postId, Long userId) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + postId, HttpStatus.NOT_FOUND));

        if (!existingPost.getUser().getId().equals(userId)) {
            throw new UserNotFoundException("User is not authorized to delete this post", HttpStatus.FORBIDDEN);
        }
        postRepository.delete(existingPost);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("post successfully deleted");
    }
    @Override
    public ResponseEntity<Post> upvotePost(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + postId, HttpStatus.NOT_FOUND));

        if (post.getUpVotedUsers().contains(user.getId())){
            post.setUpVotes(post.getUpVotes() - 1);
            post.getUpVotedUsers().remove(user.getId());
        } else {
            post.setUpVotes(post.getUpVotes() + 1);
            post.getUpVotedUsers().add(user.getId());

            if (post.getDownVotedUsers().contains(user.getId())) {
                post.setDownVotes(post.getDownVotes() - 1);
                post.getDownVotedUsers().remove(user.getId());
            }
        }
        postRepository.save(post);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @Override
    public ResponseEntity<Post> downVotePost(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + postId, HttpStatus.NOT_FOUND));

        if (post.getDownVotedUsers().contains(user.getId())){
            post.setDownVotes(post.getDownVotes() - 1);
            post.getDownVotedUsers().remove(user.getId());

        } else {
            post.setDownVotes(post.getDownVotes() + 1);
            post.getDownVotedUsers().add(user.getId());

            if (post.getUpVotedUsers().contains(user.getId())) {
                post.setUpVotes(post.getUpVotes() - 1);
                post.getUpVotedUsers().remove(user.getId());
            }
        }
        postRepository.save(post);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @Override
    public List<Post> searchPost(String keyword, String topic, HistoricalPeriod historicalPeriod){

        if (StringUtils.isEmpty(keyword) && StringUtils.isEmpty(topic) && historicalPeriod == null) {
            throw new NoCriteriaToSearchException("At least one search criteria must be provided", HttpStatus.BAD_GATEWAY);
        }
        Specification<Post> spec = Specification.where(null);

        if (keyword != null && !keyword.isEmpty()){
            spec = spec.and(PostSpecification.nameContains(keyword));
        }

        if (topic != null && !topic.isEmpty()){
            spec = spec.and(PostSpecification.topicEquals(topic));
        }

        if (historicalPeriod != null) {
            spec = spec.and(PostSpecification.historicalPeriodBetween(HistoricalPeriod.valueOf(String.valueOf(historicalPeriod))));
        }

        return postRepository.findAll(spec);
    }
}
