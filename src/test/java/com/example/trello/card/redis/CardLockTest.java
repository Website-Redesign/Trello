package com.example.trello.card.redis;

import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.card.service.CardService;
import com.example.trello.domain.user.dto.SignupRequestDto;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CardLockTest {

	@Autowired
	CardRepository cardRepository;

	@Autowired
	RedissonClient redisClient;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CardService cardService;

	private Card testCard() {
		CardRequestDto requestDto = new CardRequestDto();
		requestDto.setCardname("카드명");
		requestDto.setColor("파란색");
		requestDto.setDescription("설명들");
		return new Card(1L, requestDto);
	}

	private User testUser() {
		SignupRequestDto requestDto = new SignupRequestDto();
		requestDto.setEmail("test1@naver.com");
		requestDto.setPassword("12345678");
		requestDto.setNickname("test1");
		requestDto.setIntroduction("설명");
		requestDto.setPhoto("사진url");
		return new User(requestDto);
	}

	@Test
	@DisplayName("분산락테스트")
	void redis() throws InterruptedException {
		cardRepository.save(testCard());
		int numThreads = 100;
		CountDownLatch latch = new CountDownLatch(numThreads);
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);

		List<String> cardNames = Collections.synchronizedList(new ArrayList<>());

		for (int i = 0; i < numThreads; i++) {
			int userId = i + 1; // 사용자 번호
			int finalI = i;
			executor.submit(() -> {
				try {
					CardRequestDto requestDto = new CardRequestDto();
					requestDto.setDescription("내용");
					requestDto.setColor("색깔");
					requestDto.setCardname("CardName" + userId);
					String t = cardService.updateCard(1L, (long) userId, requestDto);
					cardNames.add(t);
				} finally {
					latch.countDown();
				}
			});
		}

		// 모든 스레드가 실행을 완료할 때까지 대기
		latch.await(10, TimeUnit.SECONDS);

		// 모든 스레드가 정상적으로 실행되었는지 확인
		Assertions.assertEquals(0, latch.getCount());
		for (String cardName : cardNames) {
			System.out.println(cardName);
		}
	}


}
