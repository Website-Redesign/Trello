package com.example.trello.domain.column.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ColumnRequestDto {
    public String column_name;
    private Long boardId;

    public ColumnRequestDto(String column_name, Long board_id) {
        this.column_name = column_name;
        this.boardId = board_id;
    }

    public String getColumnName() {
        return column_name;
    }

    public void setColumnName(String columnName) {
        this.column_name = columnName;
    }
}
