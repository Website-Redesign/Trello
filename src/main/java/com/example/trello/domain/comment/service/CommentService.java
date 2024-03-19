package com.example.trello.domain.comment.service;

import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.comment.dto.CommentRequestDto;
import com.example.trello.domain.comment.dto.CommentResponseDto;
import com.example.trello.domain.comment.entity.Comment;
import com.example.trello.domain.comment.repository.CommentRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.global.exception.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<CommentResponseDto> getCommentsByCardId(Long cardId, int page, int size) {

        Page<Comment> commentsPage = commentRepository.findByCardId(cardId,
            PageRequest.of(page, size));

        return commentsPage.map(
            comment -> new CommentResponseDto(comment.getCommentId(), comment.getNickname(),
                comment.getComment(), comment.getCreateAt()));
    }

    @Transactional
    public void updateComment(Long cardId, Long commentId, Long userId,
        CommentRequestDto commentRequestDto) {

        Comment comment = checkValidateComment(cardId, commentId, userId);

        comment.update(commentRequestDto.getComment());
    }

    @Transactional
    public void deleteComment(Long cardId, Long commentId, Long userId) {

        Comment comment = checkValidateComment(cardId, commentId, userId);

        comment.softDelete();
    }

    private Comment checkValidateComment(Long cardId, Long commentId, Long userId) {
        return commentRepository.findByCardIdAndCommentIdAndUserId(cardId, commentId, userId)
            .orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));
    }
}
