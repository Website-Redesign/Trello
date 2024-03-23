package com.example.trello.domain.card.service;

import com.example.trello.domain.card.dto.CardDeadLineRequestDto;
import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.global.exception.customException.NoEntityException;
import com.example.trello.global.exception.customException.NoPermissionException;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CardService {

	private final CardRepository cardRepository;

	private final RedissonClient redissonClient;

	private final CacheManager cacheManager;

	private final MessageSource messageSource;

	public void createCard(Long columnId, Long userId, CardRequestDto requestDto) {
		cardRepository.existsByUserIdAndColumnIdInTeam(userId, columnId).orElseThrow(
			() -> new NoPermissionException(
				messageSource.getMessage("no.permission", null, Locale.KOREA))
		);
		Card card = new Card(columnId, requestDto);
		cardRepository.save(card);
	}

	public String updateCard(Long cardId, Long userId, CardRequestDto requestDto) {
		Card card = cardRepository.findByMyId(cardId).orElseThrow(
			() -> new NoEntityException(messageSource.getMessage("no.card", null, Locale.KOREA))
		);
//		cardRepository.existsByUserIdAndColumnIdInTeam(userId, card.getColumnId()).orElseThrow(
//			() -> new NoPermissionException(messageSource.getMessage("no.permission", null, Locale.KOREA))
//		);
		RLock lock = redissonClient.getLock("card_" + cardId);
		lock.lock();
		card.update(requestDto);
		cardRepository.update(card);
		CardResponseDto responseDto = new CardResponseDto(card);
		Objects.requireNonNull(cacheManager.getCache("Card")).put(cardId, responseDto);
		lock.unlock();
		return requestDto.getCardname();
	}

	public void updateCardDeadLine(Long cardId, Long userId, CardDeadLineRequestDto requestDto) {
		Card card = cardRepository.findByMyId(cardId).orElseThrow(
			() -> new NoEntityException(messageSource.getMessage("no.card", null, Locale.KOREA))
		);
		cardRepository.existsByUserIdAndColumnIdInTeam(userId, card.getColumnId()).orElseThrow(
			() -> new NoPermissionException(
				messageSource.getMessage("no.permission", null, Locale.KOREA))
		);
		Date date = new Date();
		Date deadLine = Date.from(
			requestDto.getDeadLine().atZone(ZoneId.systemDefault()).toInstant());
		if (date.after(deadLine)) {
			throw new IllegalArgumentException(
				messageSource.getMessage("wrong.deadline", null, Locale.KOREA));
		}
		card.deadLineUpdate(requestDto);
		cardRepository.update(card);
	}

	public void deleteCard(Long cardId, Long userId) {
		Card card = cardRepository.findByMyId(cardId).orElseThrow(
			() -> new NoEntityException(messageSource.getMessage("no.card", null, Locale.KOREA))
		);
		cardRepository.existsByUserIdAndColumnIdInTeam(userId, card.getColumnId()).orElseThrow(
			() -> new NoPermissionException(
				messageSource.getMessage("no.permission", null, Locale.KOREA))
		);
		card.delete();
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "Card", key = "#cardId", cacheManager = "cacheManager", unless = "#result == null")
	public CardResponseDto getCard(Long cardId, Long userId) {
		Card card = cardRepository.findByMyId(cardId).orElseThrow(
			() -> new NoEntityException(messageSource.getMessage("no.card", null, Locale.KOREA))
		);
		cardRepository.existsByUserIdAndColumnIdInTeam(userId, card.getColumnId()).orElseThrow(
			() -> new NoPermissionException(
				messageSource.getMessage("no.permission", null, Locale.KOREA))
		);
		return new CardResponseDto(card);
	}

	public Card findCard(Long cardId) {
		return cardRepository.findById(cardId).orElseThrow(
			() -> new NoEntityException(messageSource.getMessage("no.card", null, Locale.KOREA))
		);
	}
}
