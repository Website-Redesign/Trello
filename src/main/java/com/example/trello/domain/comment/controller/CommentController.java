package com.example.trello.domain.comment.controller;

import com.example.trello.domain.comment.dto.CommentRequestDto;
import com.example.trello.domain.comment.service.CommentService;
import com.example.trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/columns/cards")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{cardId}/comments")
    public ResponseEntity<Void> createComment(
        @PathVariable Long cardId,
        @RequestBody CommentRequestDto commentRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.createComment(cardId, commentRequestDto,
            userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{cardId}/comments/{commentId}")
    public ResponseEntity<Void> updateComment(
        @PathVariable Long cardId,
        @PathVariable Long commentId,
        @RequestBody CommentRequestDto commentRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.updateComment(cardId, commentId, userDetails.getUser().getId(),
            commentRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cardId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
        @PathVariable Long cardId,
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.deleteComment(cardId, commentId, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }
}
