package com.example.trello.domain.column.service;

import com.example.trello.domain.column.dto.ColumnRequestDto;
import com.example.trello.domain.column.dto.ColumnResponseDto;
import com.example.trello.domain.column.entity.Column;
import com.example.trello.domain.column.repository.ColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;

    @Transactional
    public ColumnResponseDto createColumn(Long boardId, ColumnRequestDto requestDto) {
        Column column = new Column();
        column.setColumnName(requestDto.getColumnName());
        Column savedColumn = columnRepository.save(column);
        return new ColumnResponseDto(savedColumn);
    }

    @Transactional(readOnly = true)
    public Page<ColumnResponseDto> getColumn(Long boardId, Long columnId, Pageable pageable) {
        Column column = existsByColumnIdAndBoardId(columnId, boardId);
        Page<Column> columnPage = columnRepository.findColumnsByBoardIdAndUserId(boardId, pageable);
        return columnPage.map(ColumnResponseDto::new);
    }

    @Transactional
    public ColumnResponseDto updateColumnName(Long boardId, Long columnId, ColumnRequestDto requestDto) {
        Column column = existsByColumnIdAndBoardId(columnId, boardId);
        column.setColumnName(requestDto.getColumnName());
        Column updatedColumn = columnRepository.save(column);
        return new ColumnResponseDto(updatedColumn);
    }

    @Transactional
    public void deleteColumn(Long boardId, Long columnId) {
        columnRepository.deleteColumnByIdAndBoardIdAndUserId(columnId, boardId);
    }

    @Transactional
    public void moveColumn(Long boardId, Long columnId) {

        Column columnToMove = existsByColumnIdAndBoardId(columnId, boardId);

        List<Column> columns = columnRepository.findByBoardIdOrderByPosition(boardId);

        columns.remove(columnToMove);
        columns.add(columnToMove);

        columnRepository.saveAll(columns);
    }

    private Column existsByColumnIdAndBoardId(Long columnId, Long boardId) {
        return (Column) columnRepository.findByIdAndBoardId(columnId, boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 칼럼입니다."));
    }
}
