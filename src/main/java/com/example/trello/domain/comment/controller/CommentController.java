package com.example.trello.domain.comment.controller;

import com.example.trello.domain.comment.dto.CommentRequestDto;
import com.example.trello.domain.comment.dto.CommentResponseDto;
import com.example.trello.domain.comment.service.CommentService;
import com.example.trello.domain.notification.service.NotificationService;
import com.example.trello.global.dto.CommonResponseDto;
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
    public ResponseEntity<CommonResponseDto<Void>> createComment(
        @PathVariable Long cardId,
        @RequestBody CommentRequestDto commentRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.createComment(cardId, commentRequestDto, userDetails.getUser());
        notificationService.notifyComment(cardId);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(CommonResponseDto.<Void>builder().build());
    }

    @GetMapping
    public ResponseEntity<CommonResponseDto<Page<CommentResponseDto>>> getCommentsByCardId(
        @PathVariable Long cardId,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<CommentResponseDto> comments =
            commentService.getCommentsByCardId(cardId, page - 1, size);
        return ResponseEntity.ok().body(CommonResponseDto.<Page<CommentResponseDto>>builder()
            .date(comments)
            .build());
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto<Void>> updateComment(
        @PathVariable Long cardId,
        @PathVariable Long commentId,
        @RequestBody CommentRequestDto commentRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.updateComment(cardId, commentId, userDetails.getUser().getId(),
            commentRequestDto);
        return ResponseEntity.ok().body(CommonResponseDto.<Void>builder().build());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteComment(
        @PathVariable Long cardId,
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.deleteComment(cardId, commentId, userDetails.getUser().getId());
        return ResponseEntity.ok().body(CommonResponseDto.<Void>builder().build());
    }
}
