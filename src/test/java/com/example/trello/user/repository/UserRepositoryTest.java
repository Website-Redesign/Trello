package com.example.trello.user.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.trello.config.TestConfig;
import com.example.trello.domain.user.dto.SignupRequestDto;
import com.example.trello.domain.user.dto.UserResponseDto;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.entity.UserRoleEnum;
import com.example.trello.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;;

@DataJpaTest
@Import(TestConfig.class)
public class UserRepositoryTest {
	@Autowired
	UserRepository userRepository;

	private User testUser(){
		SignupRequestDto requestDto = new SignupRequestDto();
		requestDto.setEmail("test@naver.com");
		requestDto.setPassword("12345678");
		requestDto.setNickname("test");
		requestDto.setIntroduction("설명");
		requestDto.setPhoto("사진url");
		return new User(requestDto);
	}

	@Test
	@DisplayName("유저 저장 테스트")
	void saveTest(){
		//given
		User user = testUser();
		//when
		User testUser = userRepository.save(user);
		//then
		assertEquals(user.getId(), testUser.getId());
		assertEquals(user.getEmail(),testUser.getEmail());
	}

	@Test
	@DisplayName("유저 id로 검색 테스트")
	void findByUserTest(){
		//given
		User user = testUser();
		userRepository.save(user);
		//when
		User testUser = userRepository.findById(user.getId()).get();
		//then
		assertEquals(user.getId(), testUser.getId());
		assertEquals(user.getEmail(),testUser.getEmail());
	}

	@Test
	@DisplayName("유저 email로 검색 테스트")
	void findByEmailTest(){
		//given
		User user = testUser();
		userRepository.save(user);
		//when
		User testUser = userRepository.findByEmail(user.getEmail()).get();
		//then
		assertEquals(user.getId(), testUser.getId());
		assertEquals(user.getEmail(),testUser.getEmail());
	}

	@Test
	@DisplayName("유저 nickname로 검색 테스트")
	void findByNicknameTest(){
		//given
		User user = testUser();
		userRepository.save(user);
		//when
		User testUser = userRepository.findByNickname(user.getNickname()).get();
		//then
		assertEquals(user.getId(), testUser.getId());
		assertEquals(user.getEmail(),testUser.getEmail());
	}

	@Test
	@DisplayName("유저 전체 검색 테스트")
	void findAllUserTest(){
		//given
		User user = testUser();
		userRepository.save(user);
		Pageable pageable = PageRequest.of(0,10);
		//when
		Page<UserResponseDto> responseDto = userRepository.findAllUser(pageable).get();
		//then
		assertEquals(user.getNickname(),responseDto.getContent().get(0).getNickname());
	}

	@Test
	@DisplayName("유저 삭제 테스트")
	void deleteTest(){
		//given
		User user = testUser();
		userRepository.save(user);
		//when
		userRepository.delete(user);
		//then
		assertTrue(userRepository.findById(user.getId()).isEmpty());
	}
}
