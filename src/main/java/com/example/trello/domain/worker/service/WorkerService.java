package com.example.trello.domain.worker.service;

import com.example.trello.domain.worker.entity.Worker;
import com.example.trello.domain.worker.repository.WorkerRepository;
import com.example.trello.global.exception.customException.DeadlineExpiredException;
import com.example.trello.global.exception.customException.NoEntityException;
import com.example.trello.global.exception.customException.NoPermissionException;
import com.example.trello.global.exception.customException.UserAlreadyRegisteredException;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkerService {

	private final WorkerRepository workerRepository;

	private final MessageSource messageSource;

	@Transactional
	public void createWorker(Long cardId, Long userId) {
		Long columId = workerRepository.getColumnId(cardId).orElseThrow(
			() -> new NoEntityException(messageSource.getMessage("no.card", null, Locale.KOREA)));
		Long boardId = workerRepository.getBoardId(columId).orElseThrow(
			() -> new NoEntityException(messageSource.getMessage("no.board", null, Locale.KOREA)));
		workerRepository.findByUserIdAndBoardId(userId, boardId).orElseThrow(
			() -> new NoPermissionException(
				messageSource.getMessage("no.permission", null, Locale.KOREA)));
		if (workerRepository.findByCardIdAndUserId(cardId, userId).isPresent()) {
			throw new UserAlreadyRegisteredException(messageSource.getMessage("user.already.registered", null, Locale.KOREA));
		}
		if (workerRepository.getDeadLine(cardId)) {
			throw new DeadlineExpiredException(messageSource.getMessage("deadline.expired", null, Locale.KOREA));
		}
		Worker worker = new Worker(cardId, userId);
		workerRepository.save(worker);
	}

	@Transactional
	public void deleteWorker(Long cardId, Long userId) {
		Worker worker = workerRepository.findByCardIdAndUserId(cardId, userId).orElseThrow(
			() -> new IllegalArgumentException(messageSource.getMessage("user.not.registered", null, Locale.KOREA))
		);
		worker.delete();
	}

	@Transactional(readOnly = true)
	public List<String> getWorker(Long cardId, Long userId) {
		Long columId = workerRepository.getColumnId(cardId).orElseThrow(
			() -> new NoEntityException(messageSource.getMessage("no.card", null, Locale.KOREA)));
		Long boardId = workerRepository.getBoardId(columId).orElseThrow(
			() -> new NoEntityException(messageSource.getMessage("no.board", null, Locale.KOREA)));
		workerRepository.findByUserIdAndBoardId(userId, boardId).orElseThrow(
			() -> new NoPermissionException(
				messageSource.getMessage("no.permission", null, Locale.KOREA)));
		return workerRepository.getFindCardId(cardId);
	}

	public List<Long> findByCardId(Long cardId) {
		return workerRepository.findByCardId(cardId);
	}
}
