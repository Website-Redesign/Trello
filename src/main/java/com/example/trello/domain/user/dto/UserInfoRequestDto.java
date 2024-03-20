package com.example.trello.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoRequestDto {

	@NotBlank
	private String nickname;

	@NotBlank
	private String introduction;

	@NotBlank
	private String photo;

}
