package com.example.trello.domain.board.dto;

import com.example.trello.domain.card.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardDetailResponse {

    private Long cardId;
    private String cardName;
    private String cardColor;

    public CardDetailResponse(Card card) {
        this.cardId = card.getId();
        this.cardName = card.getCardname();
        this.cardColor = card.getColor();
    }
}
