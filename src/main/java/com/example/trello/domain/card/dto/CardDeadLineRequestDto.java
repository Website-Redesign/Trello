package com.example.trello.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDeadLineRequestDto {

	@NotBlank
	private LocalDateTime deadLine;

}
