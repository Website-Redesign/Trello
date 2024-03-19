package com.example.trello.domain.user.repository;

import com.example.trello.domain.user.entity.User;
import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<User> findByEmail(String email);
}
