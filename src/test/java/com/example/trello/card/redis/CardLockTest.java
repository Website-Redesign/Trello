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
import java.util.stream.IntStream;
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
	CardService cardService;


	@Test
	@DisplayName("분산락테스트")
	void redis() throws InterruptedException {
		int numThreads = 100;
		CountDownLatch latch = new CountDownLatch(numThreads);
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);

		for (int i = 0; i < numThreads; i++) {
			int userId = i + 1; // 사용자 번호
			executor.submit(() -> {
				try {
					CardRequestDto requestDto = new CardRequestDto();
					requestDto.setDescription("내용");
					requestDto.setColor("색깔");
					requestDto.setCardname("CardName" + userId);
					cardService.updateCard(1L, (long) userId, requestDto);
				} finally {
					latch.countDown();
				}
			});
		}

		// 모든 스레드가 실행을 완료할 때까지 대기
		latch.await(15, TimeUnit.SECONDS);

		System.out.println(cardRepository.findById(1L).get().getUpdateCount());
	}



}
