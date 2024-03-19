package com.example.trello.domain.board.service;


import com.example.trello.domain.board.dto.BoardRequest;
import com.example.trello.domain.board.dto.BoardResponse;
import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.entity.Team;
import com.example.trello.domain.board.repository.TeamRepository;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final TeamRepository boardUserRepository;

    @Transactional
    public BoardResponse createBoard(User loginUser, BoardRequest request) {
        Board savedBoard = boardRepository.save(new Board(loginUser, request));
        boardUserRepository.save(new Team(savedBoard, loginUser));
        List<Long> memberList = request.getMemberList();
        for (Long memberId : memberList) {
            boardUserRepository.save(
                new Team(savedBoard, User.builder().id(memberId).build()));
        }
        return new BoardResponse(savedBoard);
    }

    @Transactional
    public BoardResponse updateBoard(User user, BoardRequest request) {
        Board board = findBoardById(request.getId());
        checkMember(board.getId(), user.getId());
        board.update(request);
        return new BoardResponse(board);
    }

    @Transactional
    public BoardResponse inviteUser(User user, BoardRequest request) {
        Board board = findBoardById(request.getId());
        checkOwner(user.getId(), board);
        List<Long> memberList = request.getMemberList();
        for (Long memberId : memberList) {
            if (!boardUserRepository.existsByBoardIdAndUserId(board.getId(), memberId)) { // 중복 체크
                boardUserRepository.save(
                    new Team(board, User.builder().id(memberId).build()));
            }
        }
        return new BoardResponse(board);
    }

    public void checkOwner(Long userId, Board board) {
        if (userId == board.getOwner().getId()) {
            return;
        }
        throw new AccessDeniedException("오너만 접근 가능");
    }

    public void checkMember(Long boardId, Long userId) {
        if (boardUserRepository.existsByBoardIdAndUserId(boardId, userId)) {
            return;
        }
        throw new AccessDeniedException("멤버만 접근 가능");
    }

    public Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
            () -> new NoSuchElementException("보드없음")
        );
    }
}
