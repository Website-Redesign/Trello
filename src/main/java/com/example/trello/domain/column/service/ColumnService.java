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

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;

    @Transactional
    public ColumnResponseDto createColumn(Long boardId, ColumnRequestDto requestDto) {
        Column column = new Column();
        column.setColumnName(requestDto.getColumn_name());
        column.setBoardId(boardId);
        Column savedColumn = columnRepository.save(column);
        return new ColumnResponseDto(savedColumn);
    }

    @Transactional(readOnly = true)
    public ColumnResponseDto getColumn(Long boardId, Long columnId, int page, int size) {
        Column column = existsByColumnIdAndUserIdAndBoardId(columnId, boardId);
        Pageable pageable = PageRequest.of(page, size);
        Page<Column> columnPage = columnRepository.findColumnsByBoardIdAndUserId(boardId, pageable);
        columnPage.map(ColumnResponseDto::new);
        return new ColumnResponseDto(column);
    }


    @Transactional
    public ColumnResponseDto updateColumnName(Long boardId, Long columnId, ColumnRequestDto requestDto) {
        Column column = existsByColumnIdAndUserIdAndBoardId(columnId, boardId);
        column.setColumnName(requestDto.getColumn_name());
        Column updatedColumn = columnRepository.save(column);
        return new ColumnResponseDto(updatedColumn);
    }

    @Transactional
    public void deleteColumn(Long boardId, Long columnId) {
        columnRepository.deleteColumnByIdAndBoardIdAndUserId(columnId, boardId);
    }

    private Column existsByColumnIdAndUserIdAndBoardId(Long columnId, Long boardId) {
        return (Column) columnRepository.findColumnByIdAndBoardIdAndUserId(columnId, boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 컬럼을 찾을 수 없습니다"));
    }
}
