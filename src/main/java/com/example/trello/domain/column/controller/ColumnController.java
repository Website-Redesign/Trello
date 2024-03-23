package com.example.trello.domain.column.controller;

import com.example.trello.domain.column.dto.ColumnRequestDto;
import com.example.trello.domain.column.dto.ColumnResponseDto;
import com.example.trello.domain.column.service.ColumnService;
import com.example.trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards/{boardId}/columns")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping
    public ResponseEntity<ColumnResponseDto> createColumn(
            @PathVariable Long boardId,
            @RequestBody ColumnRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ColumnResponseDto responseDto = columnService.createColumn(boardId, requestDto, userDetails.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/{columnId}")
    public ResponseEntity<ColumnResponseDto> getColumn(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ColumnResponseDto responseDto = columnService.getColumn(boardId, columnId, page, size, userDetails.getUser().getId());
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{columnId}")
    public ResponseEntity<ColumnResponseDto> updateColumnName(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @RequestBody ColumnRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ColumnResponseDto responseDto = columnService.updateColumnName(boardId, columnId, requestDto, userDetails.getUser().getId());
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{columnId}")
    public ResponseEntity<Void> deleteColumn(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        columnService.deleteColumn(boardId, columnId, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }
}
