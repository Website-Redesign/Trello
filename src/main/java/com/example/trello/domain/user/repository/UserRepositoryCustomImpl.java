package com.example.trello.domain.user.repository;

import com.example.trello.domain.user.dto.UserResponseDto;
import com.example.trello.domain.user.entity.QUser;
import com.example.trello.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager entityManager;

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

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public Optional<Page<UserResponseDto>> findAllUser(Pageable pageable) {
        List<User> query = jpaQueryFactory.select(QUser.user)
            .from(QUser.user)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        List<UserResponseDto> responseDtoList = query.stream().map(UserResponseDto::new).toList();
        Long count = jpaQueryFactory.select(Wildcard.count)
            .from(QUser.user)
            .fetch()
            .get(0);
        return Optional.of(PageableExecutionUtils.getPage(responseDtoList,pageable,()->count));
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        User query = jpaQueryFactory.select(QUser.user)
            .from(QUser.user)
            .where(
                nicknameEq(nickname)
            )
            .fetchOne();
        return Optional.ofNullable(query);
    }


    private BooleanExpression emailEq(String email) {
        return Objects.nonNull(email) ? QUser.user.email.eq(email) : null;
    }

    private BooleanExpression nicknameEq(String nickname) {
        return Objects.nonNull(nickname) ? QUser.user.nickname.eq(nickname) : null;
    }

}
