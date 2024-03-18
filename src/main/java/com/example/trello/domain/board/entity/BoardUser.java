package com.example.trello.domain.board.entity;

import com.example.trello.domain.user.entity.User;
import com.example.trello.global.util.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "BoardUser")
public class BoardUser extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public BoardUser(Board board, User user) {
        this.board = board;
        this.user = user;
    }
}
