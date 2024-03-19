package com.example.trello.domain.worker.repository;

import com.example.trello.domain.column.entity.Column;
import com.example.trello.domain.worker.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long>,WorkerRepositoryCustom {

}
