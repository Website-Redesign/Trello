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
    public ResponseEntity<ColumnResponseDto> postColumn(
            @PathVariable Long boardId,
            @RequestBody ColumnRequestDto columnRequestDto) {
        ColumnResponseDto columnResponseDto = columnService.createColumn(boardId, columnRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(columnResponseDto);
    }

    @PutMapping("/{columnId}")
    public ResponseEntity<ColumnResponseDto> putColumn(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @RequestBody ColumnRequestDto columnRequestDto) {
        ColumnResponseDto columnResponseDto = columnService.updateColumnName(boardId, columnId, columnRequestDto);
        return ResponseEntity.ok(columnResponseDto);
    }
}
