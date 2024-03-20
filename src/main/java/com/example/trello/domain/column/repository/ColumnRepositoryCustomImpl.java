package com.example.trello.domain.column.repository;

import com.example.trello.domain.column.entity.Column;
import com.example.trello.domain.column.entity.QColumn;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class ColumnRepositoryCustomImpl implements ColumnRepositoryCustom {

    private final EntityManager entityManager;

    public ColumnRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<Column> findByColumnId(Long columnId, Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QColumn qColumn = QColumn.column;

        var query = queryFactory.selectFrom(qColumn)
                .where(
                        qColumn.id.eq(columnId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        var columns = query.fetch();
        long totalSize = query.fetchCount();

        return PageableExecutionUtils.getPage(columns, pageable, () -> totalSize);
    }
}
