package com.example.trello.domain.user.repository;

import com.example.trello.domain.user.dto.UserResponseDto;
import com.example.trello.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Optional<User> findByEmail(String email);

    void update(User user);

    Optional<Page<UserResponseDto>> findAllUser(Pageable pageable);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByMyId(Long id);
}
