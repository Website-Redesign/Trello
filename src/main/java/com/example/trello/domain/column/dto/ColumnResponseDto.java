package com.example.trello.domain.column.dto;

import com.example.trello.domain.column.entity.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ColumnResponseDto {
    private Long id;
    private String column_name;
    private Long board_id;

    public ColumnResponseDto(Column column) {
        this.id = column.getId();
        this.column_name = column.getColumn_name();
        this.board_id = column.getBoard_id();
    }
}
