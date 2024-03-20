package com.example.trello.card.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.trello.config.TestConfig;
import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.user.dto.SignupRequestDto;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import com.example.trello.domain.worker.entity.Worker;
import com.example.trello.domain.worker.repository.WorkerRepository;
import com.example.trello.global.util.TimeStamp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestConfig.class)
public class CardRepositoryTest {

	@Autowired
	CardRepository cardRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	WorkerRepository workerRepository;

	private Card testCard() {
		CardRequestDto requestDto = new CardRequestDto();
		requestDto.setCardname("카드명");
		requestDto.setColor("파란색");
		requestDto.setDescription("설명들");
		return new Card(1L, requestDto);
	}

	private User testUser() {
		SignupRequestDto requestDto = new SignupRequestDto();
		requestDto.setEmail("test@naver.com");
		requestDto.setPassword("12345678");
		requestDto.setNickname("test");
		requestDto.setIntroduction("설명");
		requestDto.setPhoto("사진url");
		return new User(requestDto);
	}


	@Test
	@DisplayName("카드 저장 테스트")
	void saveTest() {
		//given
		Card card = testCard();
		//when
		Card testCard = cardRepository.save(card);
		//then
		assertEquals(card.getId(), testCard.getId());
		assertEquals(card.getCardname(), testCard.getCardname());
	}

	@Test
	@DisplayName("카드 id로 검색 테스트")
	void findByCardTest() {
		//given
		Card card = testCard();
		cardRepository.save(card);
		//when
		Card testCard = cardRepository.findById(card.getId()).get();
		//then
		assertEquals(card.getId(), testCard.getId());
		assertEquals(card.getCardname(), testCard.getCardname());
	}

	@Test
	@DisplayName("카드 id로 상세 검색 테스트")
	void getFindCardTest() {
		//given
		Card card = testCard();
		cardRepository.save(card);
		User user = testUser();
		userRepository.save(user);
		Worker worker = new Worker(card.getId(), user.getId());
		workerRepository.save(worker);
		//when
		CardResponseDto responseDto = cardRepository.getFindCard(card.getId());
		//then
		assertEquals(card.getId(), responseDto.getCardId());
		assertEquals(user.getNickname(), responseDto.getWorkers().get(0));
	}

	@Test
	@DisplayName("카드 수정 테스트")
	void updateCardTest() {
		//given
		Card card = testCard();
		cardRepository.save(card);
		CardRequestDto requestDto = new CardRequestDto();
		requestDto.setCardname("카드명수정");
		requestDto.setColor("노란색");
		requestDto.setDescription("설명수정");
		card.update(requestDto);
		//when
		cardRepository.update(card);
		Card testCard = cardRepository.findById(card.getId()).get();
		//then
		assertEquals(card.getCardname(), testCard.getCardname());
		assertEquals(card.getColor(), testCard.getColor());

	}

	@Test
	@DisplayName("카드 삭제 테스트")
	void deleteTest() {
		//given
		Card card = testCard();
		cardRepository.save(card);
		//when
		cardRepository.delete(card);
		//then
		assertTrue(cardRepository.findById(card.getId()).isEmpty());
	}

}
