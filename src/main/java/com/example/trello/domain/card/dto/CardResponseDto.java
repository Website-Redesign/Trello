package com.example.trello.domain.card.dto;

import com.example.trello.domain.card.entity.Card;
import java.util.Date;
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

	private Date deadTime;

	private List<String> workers;

	public CardResponseDto(Card card, List<String> workers) {
		cardId = card.getId();
		cardname = card.getCardname();
		description = card.getDescription();
		color = card.getColor();
		deadTime = card.getDeadLine();
		this.workers = workers;
	}
}
