package com.example.trello.domain.board.repository;

import com.example.trello.domain.board.dto.BoardDetailResponse;
import com.example.trello.domain.board.entity.Board;
import java.util.List;

public interface BoardRepositoryCustom {

    List<Board> findBoardsJoinedByUser(Long userId);

    Board findBoardDetailByBoard(Long boardId);

    public BoardDetailResponse findBoardDetailByBoardJoin(Long boardId);
}
