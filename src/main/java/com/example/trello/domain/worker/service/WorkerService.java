package com.example.trello.domain.worker.service;

import com.example.trello.domain.worker.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkerService {

	public final WorkerRepository workerRepository;

	public void createWorker(Long cardId,Long userId){

	}

}
