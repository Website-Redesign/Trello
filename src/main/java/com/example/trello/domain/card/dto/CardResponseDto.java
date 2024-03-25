package com.example.trello.domain.card.dto;

import com.example.trello.domain.card.entity.Card;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardResponseDto {

	private Long cardId;

	private String cardname;

	private String description;

	private String color;

	private LocalDateTime deadLine;


	public CardResponseDto(Card card) {
		cardId = card.getId();
		cardname = card.getCardname();
		description = card.getDescription();
		color = card.getColor();
		deadLine = card.getDeadLine();
	}
}
