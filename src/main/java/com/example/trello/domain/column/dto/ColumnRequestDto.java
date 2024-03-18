package com.example.trello.domain.column.dto;

import com.example.trello.domain.column.entity.Column;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ColumnRequestDto {
    public String column_name;
    public Long board_id;
}
