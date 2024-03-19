package com.example.trello.global.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonResponseDto<T> {
	private T date;
}
