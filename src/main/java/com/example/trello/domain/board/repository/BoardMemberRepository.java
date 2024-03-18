package com.example.trello.domain.board.repository;

import com.example.trello.domain.board.entity.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {

    boolean existsByBoardIdAndUserId(Long boarId, Long userId);
}
