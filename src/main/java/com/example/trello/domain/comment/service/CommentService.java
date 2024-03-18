package com.example.trello.domain.comment.service;

import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.comment.dto.CommentRequestDto;
import com.example.trello.domain.comment.entity.Comment;
import com.example.trello.domain.comment.repository.CommentRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.global.exception.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 추후 서비스로 수정
    private final CardRepository cardRepository;

    @Transactional
    public void createComment(Long cardId, CommentRequestDto commentRequestDto, User user) {

        Card card = cardRepository.findById(cardId)
            .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        Comment comment = new Comment(commentRequestDto.getComment(), card.getId(), user.getId(),
            user.getNickname());

        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long cardId, Long commentId, Long userId) {

        Comment comment = commentRepository.findByCardIdAndCommentIdAndUserId(cardId, commentId,
            userId).orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));

        comment.softDelete();
    }
}
