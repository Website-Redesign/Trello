package com.example.trello.domain.column.repository;

import com.example.trello.domain.column.entity.Column;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;

public interface ColumnRepository extends JpaRepository<Column, Long> {

    Page<Column> findByBoardId(Long boardId, Pageable pageable);
}
