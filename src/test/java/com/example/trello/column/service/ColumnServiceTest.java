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
import org.springframework.data.domain.Pageable;

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
        requestDto.setColumnName("test");
        Column savedColumn = new Column();
        savedColumn.setColumnName("Test Column");

        when(columnRepository.save(any(Column.class))).thenReturn(savedColumn);

        // when
        ColumnResponseDto createdColumn = columnService.createColumn(boardId, requestDto);

        // then
        assertNotNull(createdColumn);
        assertEquals(savedColumn.getColumnName(), createdColumn.getColumnName()); // Compare column names
    }


    @Test
    void getColumn_ValidInput_ReturnsColumnResponseDto() {
        // given
        Long boardId = 1L;
        Long columnId = 1L;
        int page = 0;
        int size = 10;
        Column column = new Column();
        column.setColumnName("Test Column"); // Set column name

        Pageable pageable = PageRequest.of(page, size);

        Page<Column> mockPage = new PageImpl<>(List.of(column));
        when(columnRepository.findColumnsByBoardIdAndUserId(eq(boardId), eq(pageable)))
                .thenReturn(mockPage);

        // Mock behavior for when the column does not exist
        when(columnRepository.findColumnByIdAndBoardIdAndUserId(eq(columnId), eq(boardId)))
                .thenReturn(Optional.empty());

        // when
        assertThrows(IllegalArgumentException.class, () -> {
            columnService.getColumn(boardId, columnId, pageable);
        });
    }


    @Test
    void updateColumnName_ColumnNotFound_ThrowsIllegalArgumentException() {
        // given
        Long boardId = 1L;
        Long columnId = 1L;
        ColumnRequestDto requestDto = new ColumnRequestDto();

        // Mock behavior for when the column does not exist
        when(columnRepository.findByIdAndBoardId(eq(columnId), eq(boardId)))
                .thenReturn(Optional.empty());

        // when, then
        assertThrows(IllegalArgumentException.class, () -> {
            columnService.updateColumnName(boardId, columnId, requestDto);
        });
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
