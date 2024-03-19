package com.example.trello.domain.user.repository;

import com.example.trello.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    @Query("SELECT u.nickname FROM User u WHERE u.id = :id")
    Optional<String> findNicknameById(Long id);
}
