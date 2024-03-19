package com.example.trello.domain.worker.repository;

import com.example.trello.domain.card.entity.QCard;
import com.example.trello.domain.worker.entity.QWorker;
import com.example.trello.domain.worker.entity.Worker;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WorkerRepositoryCustomImpl implements WorkerRepositoryCustom{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<Worker> findByCardIdAndUserId(Long cardId, Long userId) {
		Worker query = jpaQueryFactory.select(QWorker.worker)
			.from(QWorker.worker)
			.innerJoin(QCard.card).on(QCard.card.id.eq(cardId))
			.where(
				QWorker.worker.card_id.eq(cardId),
				QWorker.worker.user_id.eq(userId)
			)
			.fetchOne();
		return Optional.of(query);
	}

	@Override
	public List<Long> findByCardId(Long cardId) {
		QWorker worker = QWorker.worker;

		return jpaQueryFactory.select(worker.id)
			.from(worker)
			.where(worker.card_id.eq(cardId))
			.fetch();
	}
}
