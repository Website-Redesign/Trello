package com.example.trello.domain.comment.entity;

import com.example.trello.global.util.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(nullable = false)
    private String comment;

    private String nickname;

    private Long userId;

    private Long cardId;

    @Enumerated(EnumType.STRING)
    private DeletionStatus deletionStatus;

    public Comment(String comment, Long userId, Long cardId, String nickname) {
        this.comment = comment;
        this.userId = userId;
        this.cardId = cardId;
        this.nickname = nickname;
        this.deletionStatus = DeletionStatus.N;
    }

    public void update(String comment) {
        this.comment = comment;
    }

    public void softDelete() {
        this.deletionStatus = DeletionStatus.Y;
    }
}
