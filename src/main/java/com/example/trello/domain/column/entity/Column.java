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
    private Long columnId;

    private String columnName;

    private Long userId;

    private Long boardId;

    public Column(String columnName, Long userId, Long boardId) {
        this.columnName = columnName;
        this.userId = userId;
        this.boardId = boardId;
    }

    public Column(ColumnRequestDto requestDto) {
        this.columnName = requestDto.getColumnName();
    }


    public void setColumnName(String columnName) {
    }
}

