package com.example.trello.domain.board.dto;

import java.util.List;
import java.util.PriorityQueue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardDetailResponse {

    private Long boardId;
    private String boardName;
    private String description;
    private String color;
    private List<ColumnDetailResponse> columns;
}
