package com.example.trello.domain.worker.repository;

import com.example.trello.domain.board.entity.QTeam;
import com.example.trello.domain.card.entity.QCard;
import com.example.trello.domain.column.entity.QColumn;
import com.example.trello.domain.user.entity.QUser;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.worker.entity.QWorker;
import com.example.trello.domain.worker.entity.Worker;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Date;
import java.util.Objects;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WorkerRepositoryCustomImpl implements WorkerRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<Worker> findByCardIdAndUserId(Long cardId, Long userId) {
		Worker query = jpaQueryFactory.select(QWorker.worker)
			.from(QWorker.worker)
			.where(
				QWorker.worker.cardId.eq(cardId),
				QWorker.worker.userId.eq(userId),
				QWorker.worker.deletedAt.isNull()
			)
			.fetchOne();
		return Optional.ofNullable(query);
	}

	@Override
	public Optional<User> findByUserIdAndBoardId(Long userId, Long boardId) {
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
	public Boolean getDeadLine(Long cardId) {
		Date query = jpaQueryFactory.select(QCard.card.deadLine)
			.from(QCard.card)
			.where(
				cardIdEq(cardId)
			).fetchOne();
		if(query==null||new Date().before(query)){
			return false;
		}
		return true;
	}


	public Optional<Long> getColumnId(Long cardId) {
		Long query = jpaQueryFactory.select(QCard.card.columnId)
			.from(QCard.card)
			.where(
				cardIdEq(cardId),
				QCard.card.deletedAt.isNull()
			)
			.fetchOne();
		return Optional.ofNullable(query);
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

	@Override
	public List<Long> findByCardId(Long cardId) {
		QWorker worker = QWorker.worker;

		return jpaQueryFactory.select(worker.userId)
			.from(worker)
			.where(
				worker.cardId.eq(cardId),
				worker.deletedAt.isNull()
			)
			.fetch();
	}

	private BooleanExpression userIdEq(Long userId) {
		return Objects.nonNull(userId) ? QTeam.team.user.id.eq(userId) : null;
	}

	private BooleanExpression boardIdEq(Long boardId) {
		return Objects.nonNull(boardId) ? QTeam.team.board.id.eq(boardId) : null;
	}

}
