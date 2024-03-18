package com.example.trello.domain.board.dto;

import com.example.trello.domain.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponse {

    private Long boardId;

    public BoardResponse(Board board) {
        this.boardId = board.getId();
    }
}
