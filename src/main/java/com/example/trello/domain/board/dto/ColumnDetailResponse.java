package com.example.trello.domain.board.dto;

import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.column.entity.Column;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ColumnDetailResponse {

    private Long columnId;
    private String columnName;
    private List<CardDetailResponse> cards = new ArrayList<>();

    public ColumnDetailResponse(Column column) {
        this.columnId = column.getColumnId();
        this.columnName = column.getColumnName();
    }

    public void addCard(Card card) {
        cards.add(new CardDetailResponse(card));
    }
}
