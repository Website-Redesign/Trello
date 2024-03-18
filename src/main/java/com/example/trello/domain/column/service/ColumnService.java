package com.example.trello.domain.column.service;

import com.example.trello.domain.column.dto.ColumnRequestDto;
import com.example.trello.domain.column.dto.ColumnResponseDto;
import com.example.trello.domain.column.entity.Column;
import com.example.trello.domain.column.repository.ColumnRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;

    public ColumnResponseDto createColumn(Long boardId, ColumnRequestDto columnRequestDto) {
        Column column = new Column(columnRequestDto);
        column.setBoardId(boardId);
        Column savedColumn = columnRepository.save(column);
        return new ColumnResponseDto(savedColumn);
    }

    public ColumnResponseDto updateColumnName(Long boardId, Long columnId, ColumnRequestDto columnRequestDto) {
        Column column = getColumnByIdAndBoardId(columnId, boardId);
        column.setColumnName(columnRequestDto.getColumn_name());
        Column updatedColumn = columnRepository.save(column);
        return new ColumnResponseDto(updatedColumn);
    }

    private Column getColumnByIdAndBoardId(Long columnId, Long boardId) {
    }
}
