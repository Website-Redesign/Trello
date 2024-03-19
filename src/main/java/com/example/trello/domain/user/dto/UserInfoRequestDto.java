package com.example.trello.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoRequestDto {
	private String nickname;

	private String introduction;

	private String photo;

}
