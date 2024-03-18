package com.example.trello.domain.column.service;

import com.example.trello.domain.column.dto.ColumnRequestDto;
import com.example.trello.domain.column.entity.Column;
import com.example.trello.domain.column.repository.ColumnRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;
}
