package com.example.trello.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.trello.domain.user.dto.ChangePasswordRequestDto;
import com.example.trello.domain.user.dto.SignupRequestDto;
import com.example.trello.domain.user.dto.UserDeleteRequestDto;
import com.example.trello.domain.user.dto.UserInfoRequestDto;
import com.example.trello.domain.user.dto.UserResponseDto;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.entity.UserRoleEnum;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.user.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	UserRepository userRepository;

	@Mock
	PasswordEncoder passwordEncoder;

	@InjectMocks
	UserService userService;

	private Pageable pageable = PageRequest.of(0, 10);

	private User testUser() {
		return new User(1L, "test@gmail.com", "12345678", "닉네임", "설명", "사진url", UserRoleEnum.USER);
	}

	@Test
	@DisplayName("유저 회원가입 테스트")
	void signupTest() {
		//given
		SignupRequestDto requestDto = new SignupRequestDto();
		requestDto.setEmail("test@gmail.com");
		requestDto.setPassword("12345678");
		requestDto.setNickname("닉네임");
		requestDto.setIntroduction("설명");
		requestDto.setPhoto("사진url");
		when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(
			Optional.ofNullable(null));
		when(userRepository.findByNickname(requestDto.getNickname())).thenReturn(
			Optional.ofNullable(null));
		//when - then
		userService.signup(requestDto);
	}

	@Test
	@DisplayName("유저 정보갱신 테스트")
	void updateUserTest() {
		//given
		UserInfoRequestDto requestDto = new UserInfoRequestDto();
		requestDto.setNickname("테스터");
		requestDto.setIntroduction("새로운설명");
		requestDto.setPhoto("새로운 사진");
		User user = testUser();
		when(userRepository.findByMyId(user.getId())).thenReturn(Optional.of(user));
		when(userRepository.findByNickname(requestDto.getNickname())).thenReturn(
			Optional.ofNullable(null));
		//when - then
		userService.updateUser(testUser().getId(), requestDto);
	}

	@Test
	@DisplayName("비밀번호 변경 테스트")
	void changePasswordTest() {
		//given
		ChangePasswordRequestDto requestDto = new ChangePasswordRequestDto();
		requestDto.setExistingPassword("12345678");
		requestDto.setNewPassword("test123456");
		User user = testUser();
		when(passwordEncoder.matches(requestDto.getExistingPassword(),
			testUser().getPassword())).thenReturn(true);
		when(userRepository.findByMyId(user.getId())).thenReturn(Optional.of(user));
		//when - then
		userService.changePassword(testUser().getId(), requestDto);
	}

	@Test
	@DisplayName("유저 삭제 테스트")
	void deleteUserTest() {
		//given
		UserDeleteRequestDto requestDto = new UserDeleteRequestDto();
		requestDto.setPassword("12345678");
		User user = testUser();
		when(
			passwordEncoder.matches(requestDto.getPassword(), testUser().getPassword())).thenReturn(
			true);
		when(userRepository.findByMyId(user.getId())).thenReturn(Optional.of(user));
		//when - then
		userService.deleteUser(user.getId(),requestDto);
	}

	@Test
	@DisplayName("특정 유저 검색 테스트")
	void getUserTest() {
		//given
		User user = testUser();
		when(userRepository.findByMyId(user.getId())).thenReturn(Optional.of(user));
		//when
		UserResponseDto responseDto = userService.getUser(user.getId());
		//then
		assertEquals(responseDto.getUserId(), user.getId());
		assertEquals(responseDto.getNickname(), user.getNickname());
	}



}
