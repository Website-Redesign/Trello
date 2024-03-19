package com.example.trello.domain.card.dto;

import com.example.trello.domain.card.entity.Card;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardResponseDto {

	private Long cardId;

	private String card_name;

	private String description;

	private String color;

	private List<String> workers;

	public CardResponseDto(Card card, List<String> workers) {
		cardId = card.getId();
		card_name = card.getCard_name();
		description = card.getDescription();
		color = card.getColor();
		this.workers = workers;
	}
}
