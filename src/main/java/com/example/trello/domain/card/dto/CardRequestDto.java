package com.example.trello.domain.card.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequestDto {

	private String cardname;

	private String description;

	private String color;

}
