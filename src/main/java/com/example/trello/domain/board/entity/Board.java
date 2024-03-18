package com.example.trello.domain.board.entity;

import com.example.trello.domain.board.dto.BoardRequest;
import com.example.trello.domain.user.entity.User;
import com.example.trello.global.util.TimeStamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private Set<BoardMember> members = new LinkedHashSet<>();

    public Board(User user, BoardRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.color = request.getColor();
        this.owner = user;
    }

    public void update(BoardRequest request) {
        if (request.getName() != null) {
            this.name = request.getName();
        }
        if (request.getDescription() != null) {
            this.description = request.getDescription();
        }
        if (request.getColor() != null) {
            this.color = request.getColor();
        }
    }

    public void addMember(User user) {
        BoardMember boardMember = new BoardMember(this, user);
        members.add(boardMember);
    }
}
