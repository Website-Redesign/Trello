package com.example.trello.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDeadLineRequestDto {

	@NotBlank
	private Date deadTime;

}
