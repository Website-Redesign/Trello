package com.example.trello.domain.column.entity;

import com.example.trello.domain.column.dto.ColumnRequestDto;
import com.example.trello.global.util.TimeStamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    private Long boardId;

    public Column(ColumnRequestDto requestDto) {
        this.column_name = requestDto.getColumn_name();
        this.boardId = requestDto.getBoard_id();
    }

    public void setColumnName(String columnName) {
        this.column_name = columnName;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

}
