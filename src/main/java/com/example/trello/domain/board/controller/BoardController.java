package com.example.trello.domain.board.controller;

import com.example.trello.domain.board.dto.BoardRequest;
import com.example.trello.domain.board.dto.BoardResponse;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.service.BoardService;
import com.example.trello.global.dto.CommonResponseDto;
import com.example.trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<CommonResponseDto<BoardResponse>> createBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody BoardRequest request
    ) {
        BoardResponse response = boardService.createBoard(userDetails.getUser(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new CommonResponseDto<>(response)
        );
    }

    @PutMapping()
    public ResponseEntity<CommonResponseDto<BoardResponse>> updateBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody BoardRequest request
    ) {
        BoardResponse response = boardService.updateBoard(userDetails.getUser(), request);
        return ResponseEntity.status(HttpStatus.OK).body(
            new CommonResponseDto<>(response)
        );
    }
}
