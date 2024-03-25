package com.example.trello.domain.column.repository;

import com.example.trello.domain.column.entity.Column;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ColumnRepositoryCustom {

    Optional<Column> findColumnByIdAndBoardIdAndUserId(Long columnId, Long boardId);

    Page<Column> findColumnsByBoardIdAndUserId(Long boardId, Pageable pageable);

    boolean deleteColumnByIdAndBoardIdAndUserId(Long columnId, Long boardId);

    List<Column> findByBoardIdOrderByPosition(Long boardId);

    Optional<Column> findByIdAndBoardId(Long columnId, Long boardId);
}
