package com.example.trello.column.repository;

import com.example.trello.domain.column.entity.Column;
import com.example.trello.domain.column.repository.ColumnRepository;
import com.example.trello.domain.column.repository.ColumnRepositoryCustom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ColumnRepositoryTest {

    @Autowired
    private ColumnRepository columnRepository;

    @MockBean(name = "columnRepositoryCustomImpl")
    private ColumnRepositoryCustom columnRepositoryCustom;

    @Test
    void testFindColumnByIdAndBoardIdAndUserId() {
        // given
        Long columnId = 1L;
        Long boardId = 1L;
        Column column = new Column();
        column.setColumnId(columnId);
        column.setBoardId(boardId);
        when(columnRepositoryCustom.findColumnByIdAndBoardIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(column));

        // when
        Optional<Column> foundColumn = columnRepositoryCustom.findColumnByIdAndBoardIdAndUserId(columnId, boardId);

        // then
        assertThat(foundColumn).isPresent();
        assertThat(foundColumn.get().getId()).isEqualTo(columnId);
        assertThat(foundColumn.get().getBoardId()).isEqualTo(boardId);
    }

    @Test
    void testFindColumnsByBoardIdAndUserId() {
        // given
        Long boardId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Column> page = columnRepository.findAll(pageRequest);
        when(columnRepositoryCustom.findColumnsByBoardIdAndUserId(anyLong(), any())).thenReturn(page);

        // when
        Page<Column> columnsPage = columnRepositoryCustom.findColumnsByBoardIdAndUserId(boardId, pageRequest);

        // then
        assertThat(columnsPage).isNotNull();
        assertThat(columnsPage.getTotalElements()).isEqualTo(page.getTotalElements());
    }

    @Test
    void testDeleteColumnByIdAndBoardIdAndUserId() {
        // given
        Long columnId = 1L;
        Long boardId = 1L;
        Column column = new Column();
        column.setColumnId(columnId);
        column.setBoardId(boardId);
        columnRepository.save(column);
        when(columnRepositoryCustom.deleteColumnByIdAndBoardIdAndUserId(anyLong(), anyLong())).thenAnswer(invocation -> {
            columnRepository.delete(column);
            return true;
        });

        // when
        boolean deletionResult = columnRepositoryCustom.deleteColumnByIdAndBoardIdAndUserId(columnId, boardId);

        // then
        assertThat(deletionResult).isTrue();
        assertThat(columnRepository.findById(columnId)).isEmpty();
    }

}
