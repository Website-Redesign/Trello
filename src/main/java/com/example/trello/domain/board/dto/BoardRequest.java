package com.example.trello.domain.board.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequest {

    private Long Id;
    private String name;
    private String description;
    private String color;
    private List<Long> memberList;
}
