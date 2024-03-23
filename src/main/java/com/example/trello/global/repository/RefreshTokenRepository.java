package com.example.trello.global.repository;

import com.example.trello.global.dto.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

	Optional<RefreshToken> findByUserId(Long userId);

}
