package com.example.trello.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestDto {
	private String existingPassword;

	private String newPassword;

}
