package com.example.trello.column.controller;

import com.example.trello.domain.column.controller.ColumnController;
import com.example.trello.domain.column.dto.ColumnRequestDto;
import com.example.trello.domain.column.dto.ColumnResponseDto;
import com.example.trello.domain.column.service.ColumnService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ColumnControllerTest {

    @InjectMocks
    private ColumnController columnController;

    @Mock
    private ColumnService columnService;

    @Test
    @DisplayName("컬럼 생성 테스트")
    void testCreateColumn() {
        // given
        Long boardId = 1L;
        ColumnRequestDto requestDto = new ColumnRequestDto();
        ColumnResponseDto mockResponseDto = new ColumnResponseDto();
        when(columnService.createColumn(boardId, requestDto)).thenReturn(mockResponseDto);

        // when
        ResponseEntity<ColumnResponseDto> responseEntity = columnController.createColumn(boardId, requestDto);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockResponseDto, responseEntity.getBody());
    }

    @Test
    @DisplayName("컬럼 조회 테스트")
    void testGetColumn() {
        // given
        Long boardId = 1L;
        Long columnId = 1L;
        int page = 0;
        int size = 10;
        ColumnResponseDto mockResponseDto = new ColumnResponseDto();
        when(columnService.getColumn(boardId, columnId, PageRequest.of(page, size))).thenReturn(new PageImpl<>(List.of(mockResponseDto)));

        // when
        ResponseEntity<Page<ColumnResponseDto>> responseEntity = columnController.getColumn(boardId, columnId, page, size);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockResponseDto, responseEntity.getBody().getContent().get(0));
    }

    @Test
    @DisplayName("컬럼 수정 테스트")
    void testUpdateColumnName() {
        // given
        Long boardId = 1L;
        Long columnId = 1L;
        ColumnRequestDto requestDto = new ColumnRequestDto();
        ColumnResponseDto mockResponseDto = new ColumnResponseDto();
        when(columnService.updateColumnName(boardId, columnId, requestDto)).thenReturn(mockResponseDto);

        // when
        ResponseEntity<ColumnResponseDto> responseEntity = columnController.updateColumnName(boardId, columnId, requestDto);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockResponseDto, responseEntity.getBody());
    }

    @Test
    @DisplayName("컬럼 삭제 테스트")
    void testDeleteColumn() {
        // given
        Long boardId = 1L;
        Long columnId = 1L;

        // when
        ResponseEntity<Void> responseEntity = columnController.deleteColumn(boardId, columnId);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(columnService, times(1)).deleteColumn(boardId, columnId);
    }

    @Test
    @DisplayName("컬럼 이동 테스트")
    void testMoveColumn() {
        // given
        Long boardId = 1L;
        Long columnId = 1L;

        // when
        ResponseEntity<Void> responseEntity = columnController.moveColumn(boardId, columnId);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(columnService, times(1)).moveColumn(boardId, columnId);
    }
}
