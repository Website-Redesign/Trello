package com.example.trello.domain.card.service;

import com.example.trello.domain.card.dto.CardDeadLineRequestDto;
import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import java.time.ZoneId;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CardService {

	private final CardRepository cardRepository;

	public void createCard(Long columnId, Long userId, CardRequestDto requestDto) {
		cardRepository.existsByUserIdAndColumnIdInTeam(userId, columnId).orElseThrow(
			() -> new IllegalArgumentException("권한이 없습니다..")
		);
		Card card = new Card(columnId, requestDto);
		cardRepository.save(card);
	}

	public void updateCard(Long cardId, Long userId, CardRequestDto requestDto) {
		Card card = cardRepository.findByMyId(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드 입니다.")
		);
		cardRepository.existsByUserIdAndColumnIdInTeam(userId, card.getColumnId()).orElseThrow(
			() -> new IllegalArgumentException("권한이 없습니다..")
		);
		card.update(requestDto);
		cardRepository.update(card);
	}

	public void updateCardDeadLine(Long cardId, Long userId, CardDeadLineRequestDto requestDto){
		Card card = cardRepository.findByMyId(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드 입니다.")
		);
		cardRepository.existsByUserIdAndColumnIdInTeam(userId, card.getColumnId()).orElseThrow(
			() -> new IllegalArgumentException("권한이 없습니다..")
		);
		Date date = new Date();
		Date deadLine = Date.from(requestDto.getDeadLine().atZone(ZoneId.systemDefault()).toInstant());
		if(date.after(deadLine)){
			throw new IllegalArgumentException("잘못된 deadLine 설정 입니다.");
		}
		card.deadLineUpdate(requestDto);
		cardRepository.update(card);
	}

	public void deleteCard(Long cardId, Long userId) {
		Card card = cardRepository.findByMyId(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드 입니다.")
		);
		cardRepository.existsByUserIdAndColumnIdInTeam(userId, card.getColumnId()).orElseThrow(
			() -> new IllegalArgumentException("권한이 없습니다..")
		);
		card.delete();
	}

	@Transactional(readOnly = true)
	public CardResponseDto getCard(Long cardId, Long userId) {
		Card card = cardRepository.findByMyId(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드 입니다.")
		);
		cardRepository.existsByUserIdAndColumnIdInTeam(userId, card.getColumnId()).orElseThrow(
			() -> new IllegalArgumentException("권한이 없습니다..")
		);
		return cardRepository.getFindCard(cardId);
	}

	public Card findCard(Long cardId) {
		return cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드 입니다.")
		);
	}
}
