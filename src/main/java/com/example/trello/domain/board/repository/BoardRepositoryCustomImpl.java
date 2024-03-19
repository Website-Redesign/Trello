package com.example.trello.domain.board.repository;


import static com.example.trello.domain.board.entity.QBoard.*;
import static com.example.trello.domain.board.entity.QTeam.team;

import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.entity.QBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> findBoardsJoinedByUser(Long userId) {
        return jpaQueryFactory.select(board)
            .from(team)
            .innerJoin(team.board, board)
            .where(team.user.id.eq(userId))
            .fetch();
    }
}
