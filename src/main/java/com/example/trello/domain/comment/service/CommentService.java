package com.example.trello.domain.comment.service;

import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.repository.CardRepository;
import com.example.trello.domain.comment.dto.CommentRequestDto;
import com.example.trello.domain.comment.entity.Comment;
import com.example.trello.domain.comment.repository.CommentRepository;
import com.example.trello.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 추후 서비스로 수정
    private final CardRepository cardRepository;

    public void createComment(Long cardId, CommentRequestDto commentRequestDto, User user) {
        Card card = cardRepository.findById(cardId)
            .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        Comment comment = new Comment(commentRequestDto.getComment(), card.getId(), user.getId());

        commentRepository.save(comment);
    }
}
