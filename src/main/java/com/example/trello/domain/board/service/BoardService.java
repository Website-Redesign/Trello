package com.example.trello.domain.board.service;


import com.example.trello.domain.board.dto.BoardDetailResponse;
import com.example.trello.domain.board.dto.BoardListResponse;
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
import java.util.stream.Collectors;
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
    private final TeamRepository teamRepository;

    @Transactional
    public BoardResponse createBoard(User loginUser, BoardRequest request) {
        Board savedBoard = boardRepository.save(new Board(loginUser, request));
        teamRepository.save(new Team(savedBoard, loginUser));
        if (request.getMemberList()!=null) {
            for (Long memberId : request.getMemberList()) {
                teamRepository.save(
                    new Team(savedBoard, User.builder().id(memberId).build()));
            }
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
            if (!teamRepository.existsByBoardIdAndUserId(board.getId(), memberId)) {
                teamRepository.save(
                    new Team(board, User.builder().id(memberId).build()));
            }
        }
        return new BoardResponse(board);

    }

    @Transactional
    public BoardResponse deleteBoard(User user, Long boardId) {
        Board board = findBoardById(boardId);
        checkOwner(user.getId(), board);
        board.delete();
        return new BoardResponse(board);
    }

    public List<BoardListResponse> findBoardCreateByUser(User user) {
        List<Board> boardsByOwner = boardRepository.findBoardsByOwner(user);
        return boardsByOwner.stream()
            .map(BoardListResponse::new)
            .collect(Collectors.toList());
    }

    public List<BoardListResponse> findBoardsJoinedByUser(User user) {
        List<Board> boardsByOwner = boardRepository.findBoardsJoinedByUser(user.getId());
        return boardsByOwner.stream()
            .map(BoardListResponse::new)
            .collect(Collectors.toList());
    }

    public BoardDetailResponse findBoardDetailByBoardId(User user, Long boardId) {
        checkMember(boardId, user.getId());
        return boardRepository.findBoardDetailByBoard(boardId);
    }

    public void checkOwner(Long userId, Board board) {
        if (userId.equals(board.getOwner().getId())) {
            return;
        }
        throw new AccessDeniedException("오너만 접근 가능");
    }

    public void checkMember(Long boardId, Long userId) {
        if (teamRepository.existsByBoardIdAndUserId(boardId, userId)) {
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
