package com.example.trello.domain.column.repository;

import com.example.trello.domain.column.entity.Column;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ColumnRepositoryCustom {
    Page<Column> findByColumnId(Long boardId, Pageable pageable);
}
