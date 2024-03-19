package com.example.trello.domain.comment.dto;

import com.example.trello.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private String comment;
    private String nickname;
    private LocalDateTime createdAt;

    public CommentResponseDto(long commentId, String nickname, String comment,
        LocalDateTime createAt) {
    }

    public CommentResponseDto toCommentResponseDto(Comment comment) {
        return new CommentResponseDto(
            comment.getCommentId(),
            comment.getNickname(),
            comment.getComment(),
            comment.getCreateAt()
        );
    }
}
