package com.example.trello.domain.card.repository;

import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.user.entity.User;
import java.util.Optional;

public interface CardRepositoryCustom {

	CardResponseDto getFindCard(Long cardId);

	Optional<User> existsByUserIdAndColumnIdInTeam(Long userId,Long columnId);

	Optional<Card> findByMyId(Long cardId);

	void update(Card card);
}
