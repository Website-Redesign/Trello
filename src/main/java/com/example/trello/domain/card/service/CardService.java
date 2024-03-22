package com.example.trello.domain.card.service;

import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {

	private final CardRepository cardRepository;

	@Transactional
	public void createCard(Long columnId, Long userId, CardRequestDto requestDto) {
		cardRepository.existsByUserIdAndColumnIdInTeam(userId, columnId).orElseThrow(
			() -> new IllegalArgumentException("권한이 없습니다..")
		);
		Card card = new Card(columnId, requestDto);
		cardRepository.save(card);
	}

	@Transactional
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

	@Transactional
	public void deleteCard(Long cardId, Long userId) {
		Card card = cardRepository.findByMyId(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드 입니다.")
		);
		cardRepository.existsByUserIdAndColumnIdInTeam(userId, card.getColumnId()).orElseThrow(
			() -> new IllegalArgumentException("권한이 없습니다..")
		);
		card.delete();
	}

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
