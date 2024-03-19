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
	public void createCard(Long columnId,Long userId, CardRequestDto requestDto){
		//team 에 속해있는지를 확인해 생성 권한 확인 로직 필요
		Card card = new Card(columnId,requestDto);
		cardRepository.save(card);
	}

	@Transactional
	public void updateCard(Long cardId,Long userId, CardRequestDto requestDto){
		Card card = cardRepository.findById(cardId).orElseThrow(
			()-> new IllegalArgumentException("존재하지 않는 카드 입니다.")
		);
		//team 에 속해있는지를 확인해 수정 권한 확인 로직 필요
		card.update(requestDto);
		cardRepository.update(card);
	}

	@Transactional
	public void deleteCard(Long cardId,Long userId){
		Card card = cardRepository.findById(cardId).orElseThrow(
			()-> new IllegalArgumentException("존재하지 않는 카드 입니다.")
		);
		//team 에 속해있는지를 확인해 삭제 권한 확인 로직 필요
		cardRepository.delete(card);
	}

	public CardResponseDto getCard(Long cardId,Long userId){
		CardResponseDto responseDto = cardRepository.getFindCard(cardId);
		//team 에 속해있는지를 확인해 열람 권한 확인 로직 필요
		return responseDto;
	}
}
