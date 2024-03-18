package com.example.trello.domain.column.entity;

import com.example.trello.domain.column.dto.ColumnRequestDto;
import com.example.trello.global.util.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "columns")
@AllArgsConstructor
@NoArgsConstructor
public class Column extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String column_name;

    private Long board_id;

    public Column(ColumnRequestDto requestDto) {
        this.column_name = requestDto.getColumn_name();
        this.board_id = requestDto.getBoard_id();
    }

    public void setColumnName(String columnName) {
        this.column_name = columnName;
    }

    public void setBoardId(Long boardId) {
        this.board_id = boardId;
    }

}
