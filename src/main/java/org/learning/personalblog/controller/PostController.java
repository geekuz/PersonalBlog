package org.learning.personalblog.controller;

import jakarta.validation.Valid;
import org.learning.personalblog.dto.PostDto;
import org.learning.personalblog.entity.Post;
import org.learning.personalblog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CREATOR')")
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostDto postDto, Authentication authentication) {
        String username = authentication.getName();
        Post createdPost = postService.createPost(postDto, username);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
}