package com.example.trello.domain.user.dto;

import com.example.trello.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

	private Long userId;

	private String nickname;

	private String introduction;

	private String photo;

	public UserResponseDto(User user){
		userId = user.getId();
		nickname = user.getNickname();
		introduction = user.getIntroduction();
		photo = user.getPhoto();
	}

}
