package com.example.trello.domain.card.repository;

import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.entity.QCard;
import com.example.trello.domain.card.entity.QWorker;
import com.example.trello.domain.user.entity.QUser;
import com.example.trello.domain.worker.entity.QWorker;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CardRepositoryCustomImpl implements CardRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	private final EntityManager entityManager;

	@Override
	public CardResponseDto getFindCard(Long cardId) {
		Card query = jpaQueryFactory.select(QCard.card)
			.from(QCard.card)
			.where(
				cardIdEq(cardId)
			)
			.fetchOne();
		//검증
		List<String> works_names = jpaQueryFactory.select(QUser.user.nickname)
			.from(QUser.user)
			.innerJoin(QWorker.worker).on(QUser.user.id.eq(QWorker.worker.user_id))
			.where(
				QWorker.worker.card_id.eq(cardId)
			).fetch()
			.stream()
			.toList();
		return new CardResponseDto(query, works_names);
	}

	@Override
	public void update(Card card) {
		entityManager.merge(card);
	}

	private BooleanExpression cardIdEq(Long cardId) {
		return Objects.nonNull(cardId) ? QCard.card.id.eq(cardId) : null;
	}

}
