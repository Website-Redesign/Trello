package com.example.trello.domain.column.controller;

import com.example.trello.domain.column.dto.ColumnRequestDto;
import com.example.trello.domain.column.dto.ColumnResponseDto;
import com.example.trello.domain.column.service.ColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards/{boardId}/columns")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping
    public ResponseEntity<ColumnResponseDto> createColumn(
            @PathVariable Long boardId,
            @RequestBody ColumnRequestDto requestDto) {
        ColumnResponseDto responseDto = columnService.createColumn(boardId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{columnId}")
    public ResponseEntity<ColumnResponseDto> getColumn(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        ColumnResponseDto responseDto = columnService.getColumn(boardId, columnId, page, size);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{columnId}")
    public ResponseEntity<ColumnResponseDto> updateColumnName(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @RequestBody ColumnRequestDto requestDto) {
        ColumnResponseDto responseDto = columnService.updateColumnName(boardId, columnId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{columnId}")
    public ResponseEntity<Void> deleteColumn(
            @PathVariable Long boardId,
            @PathVariable Long columnId) {
        columnService.deleteColumn(boardId, columnId);
        return ResponseEntity.noContent().build();
    }
}
