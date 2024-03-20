package com.example.trello.domain.worker.service;

import com.example.trello.domain.worker.entity.Worker;
import com.example.trello.domain.worker.repository.WorkerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkerService {

    public final WorkerRepository workerRepository;

    @Transactional
    public void createWorker(Long cardId, Long userId) {
        Long columId = workerRepository.getColumnId(cardId).orElseThrow(
            () -> new IllegalArgumentException("존재하지 않는 카드 입니다."));
        Long boardId = workerRepository.getBoardId(columId).orElseThrow(
            () -> new IllegalArgumentException("존재하지 않는 보드 입니다."));
        workerRepository.findByUserIdAndBoardId(userId, boardId).orElseThrow(
            () -> new IllegalArgumentException("권한이 없습니다."));
        if (workerRepository.findByCardIdAndUserId(cardId, userId).isPresent()) {
            throw new IllegalArgumentException("이미 등록된 유저 입니다.");
        }
        Worker worker = new Worker(cardId, userId);
        workerRepository.save(worker);
    }

    @Transactional
    public void deleteWorker(Long cardId, Long userId) {
        Worker worker = workerRepository.findByCardIdAndUserId(cardId, userId).orElseThrow(
            () -> new IllegalArgumentException("등록되지 않은 유저 입니다.")
        );
        workerRepository.delete(worker);
    }

    public List<Long> findByCardId(Long cardId) {
        return workerRepository.findByCardId(cardId);
    }
}
