package com.example.trello.domain.comment.service;

import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.service.CardService;
import com.example.trello.domain.comment.dto.CommentRequestDto;
import com.example.trello.domain.comment.dto.CommentResponseDto;
import com.example.trello.domain.comment.entity.Comment;
import com.example.trello.domain.comment.repository.CommentRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.service.UserService;
import com.example.trello.global.aop.TimeTrace;
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
    private final CardService cardService;
    private final UserService userService;

    @TimeTrace
    @Transactional
    public void createComment(Long cardId, CommentRequestDto commentRequestDto, User user) {

        Card card = cardService.findCard(cardId);

        String nickname = userService.findNickname(user.getId());

        Comment comment = new Comment(commentRequestDto.getComment(), user.getId(), card.getId(),
            nickname);

        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDto> getCommentsByCardId(Long cardId, int page, int size) {

        Page<Comment> commentsPage = commentRepository.findByCardId(cardId,
            PageRequest.of(page, size));

        return commentsPage.map(Comment::toCommentResponseDto);
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

    public Comment findLatestComment(Long cardId) {
        return commentRepository.findFirstByCardIdOrderByCreateAtDesc(cardId)
            .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
    }
}
