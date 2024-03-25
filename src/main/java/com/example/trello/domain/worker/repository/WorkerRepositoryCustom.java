package com.example.trello.domain.worker.repository;

import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.worker.entity.Worker;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.xml.crypto.Data;

public interface WorkerRepositoryCustom {

	Optional<Worker> findByCardIdAndUserId(Long cardId,Long userId);

	List<Long> findByCardId(Long cardId);

	Optional<User> findByUserIdAndBoardId(Long userId,Long boardId);

	Optional<Long> getBoardId(Long columnId);
	Optional<Long> getColumnId(Long cardId);

	List<String> getFindCardId(Long cardId);

	Boolean getDeadLine(Long cardId);
}
