package com.example.trello.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequestDto {

	@NotBlank
	private String cardname;

	@NotBlank
	private String description;

	@NotBlank
	private String color;


}
