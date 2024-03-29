package com.example.trello.domain.board.repository;


import static com.example.trello.domain.board.entity.QBoard.board;
import static com.example.trello.domain.board.entity.QTeam.team;
import static com.example.trello.domain.card.entity.QCard.card;
import static com.example.trello.domain.column.entity.QColumn.column;

import com.example.trello.domain.board.dto.BoardDetailResponse;
import com.example.trello.domain.board.dto.CardDetailResponse;
import com.example.trello.domain.board.dto.ColumnDetailResponse;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.entity.QBoard;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.column.entity.Column;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
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

    @Override
    public BoardDetailResponse findBoardDetailByBoard(Long boardId) {
        Board board = jpaQueryFactory.selectFrom(QBoard.board).where(QBoard.board.id.eq(boardId))
            .fetchOne();

        List<ColumnDetailResponse> columns =
            jpaQueryFactory.select(column)
            .from(column)
            .where(column.boardId.eq(boardId))
            .fetch().stream().map(ColumnDetailResponse::new).toList();

        List<Long> columnIds = columns.stream().map(ColumnDetailResponse::getColumnId).toList();

        List<Card> cards = jpaQueryFactory.select(card)
            .from(card)
            .where(card.columnId.in(columnIds))
            .fetch();

        return new BoardDetailResponse(board, columns, cards);
    }

    @Override
    public BoardDetailResponse findBoardDetailByBoardJoin(Long boardId) {
        return (BoardDetailResponse) jpaQueryFactory
            .select(Projections.constructor(BoardDetailResponse.class,
                board.id, board.name, board.description, board.color,
                Projections.constructor(ColumnDetailResponse.class,
                    column.columnId, column.columnName,
                    Projections.constructor(CardDetailResponse.class,
                        card.id, card.cardname, card.color))))
            .from(board)
            .leftJoin(column).on(board.id.eq(column.boardId))
            .leftJoin(card).on(column.columnId.eq(card.columnId))
            .where(board.id.eq(boardId))
            .fetch();
    }
}

//전부조인으로 한방쿼리 날리고 내부에서 데이터 만들어서
//정리하기

