package com.example.trello.domain.board.dto;

import com.example.trello.domain.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardListResponse {

    private Long id;
    private String name;
    private String color;

    public BoardListResponse(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.color = board.getColor();
    }
}
