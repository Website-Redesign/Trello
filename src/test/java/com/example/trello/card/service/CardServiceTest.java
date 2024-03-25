package com.example.trello.card.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.card.service.CardService;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.entity.UserRoleEnum;
import io.lettuce.core.RedisClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

	@Mock
	CardRepository cardRepository;

	@InjectMocks
	CardService cardService;

	private Pageable pageable = PageRequest.of(0, 10);

	private Card testCard() {
		CardRequestDto requestDto = new CardRequestDto();
		requestDto.setCardname("카드명");
		requestDto.setColor("파란색");
		requestDto.setDescription("설명들");
		return new Card(1L, requestDto);
	}

	private User testUser() {
		return new User(1L, "test@gmail.com", "12345678", "닉네임", "설명", "사진url", UserRoleEnum.USER);
	}

	@Test
	@DisplayName("카드 생성 테스트")
	void createCard() {
		//given
		User user = testUser();
		CardRequestDto requestDto = new CardRequestDto();
		requestDto.setCardname("카드명");
		requestDto.setColor("파란색");
		requestDto.setDescription("설명들");
		when(cardRepository.existsByUserIdAndColumnIdInTeam(1L, 1l)).thenReturn(Optional.of(user));
		//when - then
		cardService.createCard(1L, user.getId(), requestDto);
	}

	@Test
	@DisplayName("카드 수정 테스트")
	void updateCard() {
		//given
		User user = testUser();
		Card card = testCard();
		CardRequestDto requestDto = new CardRequestDto();
		requestDto.setCardname("카드명");
		requestDto.setColor("파란색");
		requestDto.setDescription("설명들");
		when(cardRepository.findByMyId(1L)).thenReturn(Optional.of(card));
		when(cardRepository.existsByUserIdAndColumnIdInTeam(1L, 1l)).thenReturn(Optional.of(user));
		//when - then
		cardService.updateCard(1L, user.getId(), requestDto);
	}

	@Test
	@DisplayName("카드 삭제 테스트")
	void deleteCard() {
		//given
		User user = testUser();
		Card card = testCard();
		CardRequestDto requestDto = new CardRequestDto();
		requestDto.setCardname("카드명");
		requestDto.setColor("파란색");
		requestDto.setDescription("설명들");
		when(cardRepository.findByMyId(1L)).thenReturn(Optional.of(card));
		when(cardRepository.existsByUserIdAndColumnIdInTeam(1L, 1l)).thenReturn(Optional.of(user));
		//when - then
		cardService.deleteCard(1L, user.getId());
	}

	@Test
	@DisplayName("카드 정보보기 테스트")
	void getCard() {
		//given
		User user = testUser();
		Card card = testCard();
		CardRequestDto requestDto = new CardRequestDto();
		requestDto.setCardname("카드명");
		requestDto.setColor("파란색");
		requestDto.setDescription("설명들");
		List<String> names = new ArrayList<>();
		when(cardRepository.findByMyId(card.getId())).thenReturn(Optional.of(card));
		when(cardRepository.existsByUserIdAndColumnIdInTeam(user.getId(), 1l)).thenReturn(
			Optional.of(user));
		//when
		CardResponseDto responseDto = cardService.getCard(card.getId(), user.getId());
		//then
		assertEquals(responseDto.getCardId(), card.getId());
		assertEquals(responseDto.getCardname(), card.getCardname());
	}


}
