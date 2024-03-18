package com.example.trello.domain.user.repository;

import com.example.trello.domain.user.entity.QUser;
import com.example.trello.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> findByEmail(String email) {
        User query = jpaQueryFactory.select(QUser.user)
                .from(QUser.user)
                .where(
                        emailEq(email)
                )
                .fetchOne();
        return Optional.ofNullable(query);
    }

    private BooleanExpression emailEq(String email) {
        return Objects.nonNull(email) ? QUser.user.email.eq(email) : null;
    }
}
