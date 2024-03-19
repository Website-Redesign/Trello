package com.example.trello.domain.card.repository;

import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.entity.Card;
import java.util.Optional;

public interface CardRepositoryCustom {

	CardResponseDto getFindCard(Long cardId);

	void update(Card card);

}
