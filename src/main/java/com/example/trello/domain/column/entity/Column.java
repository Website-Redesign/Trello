package com.example.trello.domain.column.entity;

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

}
