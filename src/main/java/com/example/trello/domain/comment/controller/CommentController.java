package com.example.trello.domain.comment.controller;

import com.example.trello.domain.comment.dto.CommentRequestDto;
import com.example.trello.domain.comment.dto.CommentResponseDto;
import com.example.trello.domain.comment.service.CommentService;
import com.example.trello.domain.notification.service.NotificationService;
import com.example.trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards/{cardId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Void> createComment(
        @PathVariable Long cardId,
        @RequestBody CommentRequestDto commentRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        notificationService.notifyComment(cardId);
        commentService.createComment(cardId, commentRequestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponseDto>> getCommentsByCardId(
        @PathVariable Long cardId,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<CommentResponseDto> comments =
            commentService.getCommentsByCardId(cardId, page - 1, size);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{commentId}")
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

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
        @PathVariable Long cardId,
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.deleteComment(cardId, commentId, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }
}
