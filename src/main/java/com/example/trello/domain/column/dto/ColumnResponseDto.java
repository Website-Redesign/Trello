package com.example.trello.domain.column.dto;

import com.example.trello.domain.column.entity.Column;
import com.example.trello.global.util.TimeStamp;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ColumnResponseDto extends TimeStamp {
    private Long columnId;
    private String columnName;
    private Long boardId;

    public ColumnResponseDto(Column column) {
        this.columnName = column.getColumnName();
        this.boardId = column.getBoardId();
    }
}
