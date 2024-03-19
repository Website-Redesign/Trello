package com.example.trello.domain.worker.repository;

import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.worker.entity.Worker;
import java.util.Optional;

public interface WorkerRepositoryCustom {

	Optional<Worker> findByCardIdAndUserId(Long cardId,Long userId);

	Optional<User> findByUserIdAndBoardId(Long userId,Long boardId);

	Optional<Long> getBoardId(Long columnId);
	Optional<Long> getColumnId(Long cardId);
}
