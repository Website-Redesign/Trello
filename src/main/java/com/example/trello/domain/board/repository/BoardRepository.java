package com.example.trello.domain.board.repository;


import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    List<Board> findBoardsByOwner(User user);
}
