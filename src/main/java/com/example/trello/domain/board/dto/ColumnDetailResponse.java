package com.example.trello.domain.board.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ColumnDetailResponse {

    private Long columnId;
    private String columnName;
    private List<CardDetailResponse> cards;
}
