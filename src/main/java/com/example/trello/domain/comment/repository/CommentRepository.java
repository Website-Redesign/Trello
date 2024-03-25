package com.example.trello.domain.comment.repository;

import com.example.trello.domain.comment.entity.Comment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    Optional<Comment> findByCardIdAndCommentIdAndUserId(Long cardId, Long commentId, Long userId);

    Optional<Comment> findFirstByCardIdOrderByCreateAtDesc(Long cardId);
}
