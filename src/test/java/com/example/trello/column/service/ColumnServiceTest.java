package com.example.trello.column.service;

import com.example.trello.domain.column.dto.ColumnRequestDto;
import com.example.trello.domain.column.dto.ColumnResponseDto;
import com.example.trello.domain.column.entity.Column;
import com.example.trello.domain.column.repository.ColumnRepository;
import com.example.trello.domain.column.service.ColumnService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ColumnServiceTest {

    @Mock
    private ColumnRepository columnRepository;

    @InjectMocks
    private ColumnService columnService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createColumn_ValidInput_ReturnsColumnResponseDto() {
        // given
        Long boardId = 1L;
        ColumnRequestDto requestDto = new ColumnRequestDto();
        Column savedColumn = new Column();
        savedColumn.setColumnName("Test Column");
        savedColumn.setBoardId(boardId);

        when(columnRepository.save(any(Column.class))).thenReturn(savedColumn);

        // when
        ColumnResponseDto createdColumn = columnService.createColumn(boardId, requestDto);

        // then
        assertNotNull(createdColumn);
        assertEquals(savedColumn.getColumn_name(), createdColumn.getColumn_name());
    }

    @Test
    void getColumn_ValidInput_ReturnsColumnResponseDto() {
        // given
        Long boardId = 1L;
        Long columnId = 1L;
        int page = 0;
        int size = 10;
        Column column = new Column();
        column.setColumnId(columnId);
        column.setColumnName("Test Column");
        column.setBoardId(boardId);

        Page<Column> mockPage = new PageImpl<>(List.of(column)); // Mock Page 객체 생성
        when(columnRepository.findColumnsByBoardIdAndUserId(eq(boardId), any(PageRequest.class)))
                .thenReturn(mockPage); // Mock Page 객체 반환 설정

        when(columnRepository.findColumnByIdAndBoardIdAndUserId(eq(columnId), eq(boardId)))
                .thenReturn(Optional.of(column));

        // when
        ColumnResponseDto retrievedColumn = columnService.getColumn(boardId, columnId, page, size);

        // then
        assertNotNull(retrievedColumn);
        assertEquals(column.getColumn_name(), retrievedColumn.getColumn_name());

    }

    @Test
    void updateColumnName_ValidInput_ReturnsColumnResponseDto() {
        // given
        Long boardId = 1L;
        Long columnId = 1L;
        ColumnRequestDto requestDto = new ColumnRequestDto();
        Column column = new Column();
        column.setColumnId(columnId);
        column.setColumnName("Test Column");
        column.setBoardId(boardId);

        when(columnRepository.findColumnByIdAndBoardIdAndUserId(eq(columnId), eq(boardId)))
                .thenReturn(Optional.of(column));
        when(columnRepository.save(any(Column.class))).thenReturn(column);

        // when
        ColumnResponseDto updatedColumn = columnService.updateColumnName(boardId, columnId, requestDto);

        // then
        assertNotNull(updatedColumn);
        assertEquals(requestDto.getColumnName(), updatedColumn.getColumn_name());
    }

    @Test
    void deleteColumn_ValidInput_VerifyRepositoryCall() {
        // given
        Long boardId = 1L;
        Long columnId = 1L;

        // when
        columnService.deleteColumn(boardId, columnId);

        // then
        verify(columnRepository, times(1)).deleteColumnByIdAndBoardIdAndUserId(columnId, boardId);
    }
}
