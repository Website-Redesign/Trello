package com.example.trello.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardDetailResponse {

    private Long cardId;
    private String cardName;
    private String cardColor;
}
