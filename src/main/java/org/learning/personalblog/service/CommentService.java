package org.learning.personalblog.service;

import jakarta.persistence.EntityNotFoundException;
import org.learning.personalblog.dto.CommentDto;
import org.learning.personalblog.entity.Comment;
import org.learning.personalblog.entity.Post;
import org.learning.personalblog.entity.User;
import org.learning.personalblog.repository.CommentRepository;
import org.learning.personalblog.repository.PostRepository;
import org.learning.personalblog.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Comment createComment(Long postId, CommentDto commentDto, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Comment newComment = new Comment();
        newComment.setContent(commentDto.getContent());
        newComment.setPost(post);
        newComment.setUser(author);

        return commentRepository.save(newComment);
    }
}