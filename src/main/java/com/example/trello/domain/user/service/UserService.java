package com.example.trello.domain.user.service;

import com.example.trello.domain.user.dto.ChangePasswordRequestDto;
import com.example.trello.domain.user.dto.SignupRequestDto;
import com.example.trello.domain.user.dto.UserDeleteRequestDto;
import com.example.trello.domain.user.dto.UserInfoRequestDto;
import com.example.trello.domain.user.dto.UserResponseDto;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void signup(SignupRequestDto requestDto) {
		if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
			throw new IllegalArgumentException("중복된 이름을 가진 회원이 있습니다.");
		}
		String password = passwordEncoder.encode(requestDto.getPassword());
		requestDto.setPassword(password);

		User user = new User(requestDto);
		userRepository.save(user);
	}

	@Transactional
	public void updateUser(Long userId, UserInfoRequestDto requestDto) {
		User user = userRepository.findById(userId).orElseThrow(
			() -> new IllegalArgumentException("계정 정보가 없습니다.")
		);
		user.update(requestDto);
		userRepository.update(user);
	}

	@Transactional
	public void changePassword(Long userId, ChangePasswordRequestDto requestDto) {
		User user = userRepository.findById(userId).orElseThrow(
			() -> new IllegalArgumentException("계정 정보가 없습니다.")
		);
		if (!passwordEncoder.matches(user.getPassword(), requestDto.getExistingPassword())) {
			throw new AccessDeniedException("비밀번호가 틀렸습니다.");
		}
		String password = passwordEncoder.encode(requestDto.getNewPassword());
		user.changePassword(password);
		userRepository.update(user);
	}

	@Transactional
	public void deleteUser(Long userId, UserDeleteRequestDto requestDto) {
		User user = userRepository.findById(userId).orElseThrow(
			() -> new IllegalArgumentException("계정 정보가 없습니다.")
		);
		if (!passwordEncoder.matches(user.getPassword(), requestDto.getPassword())) {
			throw new AccessDeniedException("비밀번호가 틀렸습니다.");
		}
		userRepository.delete(user);
	}

	public UserResponseDto getUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(
			() -> new IllegalArgumentException("계정 정보가 없습니다.")
		);
		return new UserResponseDto(user);
	}

	public Page<UserResponseDto> getAllUsers() {
		return userRepository.findAllUser(PageRequest.of(0, 10)).orElseThrow(
			() -> new IllegalArgumentException("현재 가입된 유저가 없습니다.")
		);
	}

}
