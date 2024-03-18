package com.example.trello.domain.comment.repository;

import com.example.trello.domain.comment.entity.Comment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByCardIdAndCommentIdAndUserId(Long cardId, Long commentId, Long userId);

}
