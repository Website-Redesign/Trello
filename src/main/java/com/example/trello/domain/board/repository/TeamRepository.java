package com.example.trello.domain.board.repository;

import com.example.trello.domain.board.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

    boolean existsByBoardIdAndUserId(Long boarId, Long userId);
}
