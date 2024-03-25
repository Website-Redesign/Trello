package com.example.trello.domain.column.service;

import com.example.trello.domain.column.dto.ColumnRequestDto;
import com.example.trello.domain.column.dto.ColumnResponseDto;
import com.example.trello.domain.column.entity.Column;
import com.example.trello.domain.column.repository.ColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;

    @Transactional
    public void createColumn(Long boardId, ColumnRequestDto columnRequestDto) {
        Column column = new Column(columnRequestDto);
        column.setBoardId(boardId);
        columnRepository.save(column);
    }

    @Transactional(readOnly = true)
    public ColumnResponseDto getColumns(Long boardId, Long columnId, int page, int size) {
        Column column = columnRepository.findById(columnId)
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 컬럼을 찾을 수 없습니다:" + columnId));
        Page<Column> columnPage = columnRepository.findColumnsByBoardId(boardId, PageRequest.of(page - 1, size));
        return new ColumnResponseDto((Column) columnPage.getContent());
    }

    @Transactional
    public void updateColumnName(Long boardId, Long columnId, ColumnRequestDto columnRequestDto) {
        Column column = columnRepository.findById(columnId)
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 컬럼을 찾을 수 없습니다:" + columnId));
        column.setColumnName(columnRequestDto.getColumn_name());
        columnRepository.save(column);
    }

    @Transactional
    public void deleteColumns(Long boardId, Long columnId) {
        columnRepository.deleteById(columnId);
    }
}
