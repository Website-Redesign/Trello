package com.example.trello.domain.board.dto;

import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.column.entity.Column;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardDetailResponse {

    private Long boardId;
    private String boardName;
    private String description;
    private String color;
    private List<ColumnDetailResponse> columns = new ArrayList<>();

    public BoardDetailResponse(Board board, List<ColumnDetailResponse> columns, List<Card> cards) {
        this.boardId = board.getId();
        this.boardName = board.getName();
        this.description = board.getDescription();
        this.color = board.getColor();
        for (ColumnDetailResponse column : columns) {
            for (Card card : cards) {
                if(card.getColumnId().equals(column.getColumnId())) {
                    column.addCard(card);
                }
            }
            this.columns = columns;
        }
    }
}
