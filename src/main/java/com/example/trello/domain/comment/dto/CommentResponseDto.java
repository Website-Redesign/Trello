package com.example.trello.domain.comment.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private String comment;
    private String nickname;
    private LocalDateTime createdAt;

    public CommentResponseDto(Long commentId, String nickname, String comment,
        LocalDateTime createdAt) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.comment = comment;
        this.createdAt = createdAt;
    }
}
