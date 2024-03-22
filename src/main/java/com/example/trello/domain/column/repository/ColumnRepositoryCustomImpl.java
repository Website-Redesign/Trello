package com.example.trello.domain.column.repository;

import com.example.trello.domain.column.entity.Column;
import com.example.trello.domain.column.entity.QColumn;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ColumnRepositoryCustomImpl implements ColumnRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QColumn qColumn = QColumn.column;

    @Override
    public Optional<Column> findColumnByIdAndBoardIdAndUserId(Long columnId, Long boardId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .select(qColumn)
                        .from(qColumn)
                        .where(
                                qColumn.id.eq(columnId),
                                qColumn.id.eq(boardId)
                        )
                        .fetchOne()
        );
    }

    @Override
    public Page<Column> findColumnsByBoardIdAndUserId(Long boardId, Pageable pageable) {
        JPAQuery<Column> query = jpaQueryFactory
                .select(qColumn)
                .from(qColumn)
                .where(qColumn.id.eq(boardId));

        query = Objects.requireNonNull(pageable).isPaged() ? query.offset(pageable.getOffset()).limit(pageable.getPageSize()) : query;

        List<Column> content = query.fetch();
        return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
    }

    @Override
    public boolean deleteColumnByIdAndBoardIdAndUserId(Long columnId, Long boardId) {
        jpaQueryFactory
                .delete(qColumn)
                .where(
                        qColumn.id.eq(columnId),
                        qColumn.id.eq(boardId)
                )
                .execute();
        return false;
    }
}
