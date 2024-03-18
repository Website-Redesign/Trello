package com.example.trello.domain.column.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ColumnResponseDto {
    private Long id;
    private String column_name;
    private Long board_id;

    public ColumnResponseDto(Long id, String column_name, Long board_id) {
        this.id = id;
        this.column_name = column_name;
        this.board_id = board_id;
    }
}
