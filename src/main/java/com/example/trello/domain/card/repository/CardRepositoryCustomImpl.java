package com.example.trello.domain.card.repository;

import com.example.trello.domain.board.entity.QTeam;
import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.entity.QCard;
import com.example.trello.domain.column.entity.QColumn;
import com.example.trello.domain.user.entity.QUser;
import com.example.trello.domain.user.entity.User;
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
		Card card = findByMyId(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드 입니다."));
		List<String> works_names = jpaQueryFactory.select(QUser.user.nickname)
			.from(QUser.user)
			.innerJoin(QWorker.worker).on(QUser.user.id.eq(QWorker.worker.userId))
			.where(
				QWorker.worker.cardId.eq(cardId),
				QWorker.worker.deletedAt.isNull(),
				QUser.user.deletedAt.isNull()
			).fetch()
			.stream()
			.toList();
		return new CardResponseDto(card, works_names);
	}

	@Override
	public Optional<User> existsByUserIdAndColumnIdInTeam(Long userId, Long columnId) {
		Long boardId = getBoardId(columnId).orElseThrow(
			() -> new IllegalArgumentException("해당 하는 Id의 보드가 없습니다.")
		);
		User query = jpaQueryFactory.select(QTeam.team.user)
			.from(QTeam.team)
			.where(
				userIdEq(userId),
				boardIdEq(boardId),
				QTeam.team.deletedAt.isNull()
			).fetchOne();

		return Optional.ofNullable(query);
	}

	@Override
	public Optional<Card> findByMyId(Long cardId) {
		Card query = jpaQueryFactory.select(QCard.card)
			.from(QCard.card)
			.where(
				cardIdEq(cardId),
				QCard.card.deletedAt.isNull()
			)
			.fetchOne();
		return Optional.ofNullable(query);
	}

	@Override
	public void update(Card card) {
		entityManager.merge(card);
	}

	public Optional<Long> getBoardId(Long columnId) {
		Long query = jpaQueryFactory.select(QColumn.column.boardId)
			.from(QColumn.column)
			.where(
				columnIdEq(columnId),
				QColumn.column.deletedAt.isNull()
			)
			.fetchOne();
		return Optional.ofNullable(query);
	}

	private BooleanExpression cardIdEq(Long cardId) {
		return Objects.nonNull(cardId) ? QCard.card.id.eq(cardId) : null;
	}

	private BooleanExpression columnIdEq(Long columnId) {
		return Objects.nonNull(columnId) ? QColumn.column.id.eq(columnId) : null;
	}

	private BooleanExpression userIdEq(Long userId) {
		return Objects.nonNull(userId) ? QTeam.team.user.id.eq(userId) : null;
	}

	private BooleanExpression boardIdEq(Long boardId) {
		return Objects.nonNull(boardId) ? QTeam.team.board.id.eq(boardId) : null;
	}

}
