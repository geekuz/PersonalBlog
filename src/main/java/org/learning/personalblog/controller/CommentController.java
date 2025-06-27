package org.learning.personalblog.controller;

import jakarta.validation.Valid;
import org.learning.personalblog.dto.CommentDto;
import org.learning.personalblog.entity.Comment;
import org.learning.personalblog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/comments") // Nested under posts for a good RESTful design
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('READER', 'CREATOR')")
    public ResponseEntity<Comment> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentDto commentDto,
            Authentication authentication) {

        String username = authentication.getName();
        Comment createdComment = commentService.createComment(postId, commentDto, username);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
}