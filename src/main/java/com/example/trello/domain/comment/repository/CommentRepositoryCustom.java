package com.example.trello.domain.comment.repository;

import com.example.trello.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CommentRepositoryCustom {

    Page<Comment> findByCardId(Long cardId, PageRequest pageRequest);
}
