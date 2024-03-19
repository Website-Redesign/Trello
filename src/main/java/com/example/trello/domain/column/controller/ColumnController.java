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
    public ResponseEntity<Void> postColumn(
            @PathVariable Long boardId,
            @RequestBody ColumnRequestDto columnRequestDto) {
        columnService.createColumn(boardId, columnRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{columnId}")
    public ResponseEntity<ColumnResponseDto> getColumn(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size){
        ColumnResponseDto columnResponseDto = columnService.getColumns(boardId, columnId, page, size);
        return ResponseEntity.ok(columnResponseDto);
    }

    @PutMapping("/{columnId}")
    public ResponseEntity<Void> putColumn(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @RequestBody ColumnRequestDto columnRequestDto) {
        columnService.updateColumnName(boardId, columnId, columnRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{columnId}")
    public ResponseEntity<Void> deleteColumn(
            @PathVariable Long boardId,
            @PathVariable Long columnId) {
        columnService.deleteColumns(boardId, columnId);
        return ResponseEntity.ok().build();
    }
}
