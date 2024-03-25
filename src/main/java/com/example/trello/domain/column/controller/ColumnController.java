package com.example.trello.domain.column.controller;

import com.example.trello.domain.column.dto.ColumnRequestDto;
import com.example.trello.domain.column.dto.ColumnResponseDto;
import com.example.trello.domain.column.service.ColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        ColumnResponseDto columnResponseDto = columnService.createColumn(boardId, requestDto);
        return ResponseEntity.ok().body(columnResponseDto);
    }

    @GetMapping("/{columnId}")
    public ResponseEntity<Page<ColumnResponseDto>> getColumn(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ColumnResponseDto> columnResponsePage = columnService.getColumn(boardId, columnId, pageable);
        return ResponseEntity.ok().body(columnResponsePage);
    }

    @PutMapping("/{columnId}")
    public ResponseEntity<ColumnResponseDto> updateColumnName(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @RequestBody ColumnRequestDto requestDto) {
        ColumnResponseDto columnResponseDto = columnService.updateColumnName(boardId, columnId, requestDto);
        return ResponseEntity.ok().body(columnResponseDto);
    }

    @DeleteMapping("/{columnId}")
    public ResponseEntity<Void> deleteColumn(
            @PathVariable Long boardId,
            @PathVariable Long columnId) {
        columnService.deleteColumn(boardId, columnId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{columnId}/move")
    public ResponseEntity<Void> moveColumn(
            @PathVariable Long boardId,
            @PathVariable Long columnId) {
        columnService.moveColumn(boardId, columnId);
        return ResponseEntity.ok().build();
    }
}
