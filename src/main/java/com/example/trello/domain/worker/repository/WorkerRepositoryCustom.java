package com.example.trello.domain.worker.repository;

import com.example.trello.domain.worker.entity.Worker;
import java.util.List;
import java.util.Optional;

public interface WorkerRepositoryCustom {

	Optional<Worker> findByCardIdAndUserId(Long cardId,Long userId);

	List<Long> findByCardId(Long cardId);
}
