package com.example.trello.domain.board.service;


import com.example.trello.domain.board.dto.BoardRequest;
import com.example.trello.domain.board.dto.BoardResponse;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.entity.BoardUser;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.board.repository.BoardUserRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardUserRepository boardUserRepository;
    //수정 필요
    private final UserRepository userRepository;

    public BoardResponse createBoard(User user, BoardRequest request) {
        Board savedBoard = boardRepository.save(new Board(user, request));
        List<Long> memberList = request.getMemberList();
        if (memberList.isEmpty()) {
            for (Long userId : memberList) {
                //유저 받는 부분 UserService 사용으로 수정해야함.
                User member = userRepository.findById(userId).orElseThrow(
                    () -> new NoSuchElementException("회원 없음")
                );
                boardUserRepository.save(new BoardUser(savedBoard, member));
            }
        }
        return new BoardResponse(savedBoard);
    }

    public BoardResponse updateBoard(User user, BoardRequest request) {
        Board board = findBoardById(request.getId());
        checkMember(board.getId(), user.getId());
        board.update(request);
        return new BoardResponse(board);
    }

    public boolean checkMember(Long boardId, Long userId) {
        if(boardUserRepository.existsByBoardIdAndUserId(boardId, userId)) return true;
        throw new AccessDeniedException("멤버만 접근 가능");
    }
    public Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
            () -> new NoSuchElementException("보드없음")
        );
    }
}