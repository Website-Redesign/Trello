package com.example.trello.domain.board.entity;

import com.example.trello.domain.board.dto.BoardRequest;
import com.example.trello.domain.user.entity.User;
import com.example.trello.global.util.TimeStamp;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "Board")
public class Board extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String color;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    public Board(User user, BoardRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.color = request.getColor();
        this.createdBy = user;
    }

    public void update(BoardRequest request) {
        if (request.getName() != null) this.name = request.getName();
        if (request.getDescription() != null) this.description = request.getDescription();
        if (request.getColor() != null) this.color = request.getColor();
    }
}
