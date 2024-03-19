package com.example.trello.domain.comment.repository;

import com.example.trello.domain.comment.entity.Comment;
import com.example.trello.domain.comment.entity.DeletionStatus;
import com.example.trello.domain.comment.entity.QComment;
import com.example.trello.global.exception.CommentNotFoundException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QComment comment = QComment.comment1;

    @Override
    public Page<Comment> findByCardId(Long cardId, PageRequest pageRequest) {

        if (cardId == null) {
            throw new CommentNotFoundException("해당 댓글이 존재하지 않습니다.");
        }

        BooleanExpression predicate = comment.cardId.eq(cardId)
                .and(comment.deletionStatus.eq(DeletionStatus.NOT_DELETED));

        List<Comment> comments = jpaQueryFactory
                .selectFrom(comment)
                .where(predicate)
                .orderBy(comment.createAt.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        long totalCount = jpaQueryFactory
                .selectFrom(comment)
                .where(predicate)
                .fetchCount();

        return new PageImpl<>(comments, pageRequest, totalCount);
    }
}
