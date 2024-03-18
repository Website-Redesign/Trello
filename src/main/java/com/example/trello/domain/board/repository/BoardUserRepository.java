package com.example.trello.domain.board.repository;

import com.example.trello.domain.board.entity.BoardUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {

    boolean existsByBoardIdAndUserId(Long boarId, Long userId);
}
