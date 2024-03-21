package com.example.trello.comment;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.service.CardService;
import com.example.trello.domain.comment.dto.CommentRequestDto;
import com.example.trello.domain.comment.dto.CommentResponseDto;
import com.example.trello.domain.comment.entity.Comment;
import com.example.trello.domain.comment.entity.DeletionStatus;
import com.example.trello.domain.comment.repository.CommentRepository;
import com.example.trello.domain.comment.service.CommentService;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.entity.UserRoleEnum;
import com.example.trello.domain.user.service.UserService;
import com.example.trello.global.exception.CommentNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;


@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserService userService;
    @Mock
    private CardService cardService;

    Long testCardId;
    Long testUserId;
    Long testCommentId;
    User testUser;
    Card testCard;
    Comment testComment;
    CommentRequestDto commentRequestDto;

    private Card testCard() {
        CardRequestDto requestDto = new CardRequestDto();
        requestDto.setCardname("카드명");
        requestDto.setColor("파란색");
        requestDto.setDescription("설명들");
        return new Card(1L, requestDto);
    }

    private User testUser() {
        return new User(2L, "test@gmail.com", "12345678", "nickname", "설명", "사진url",
            UserRoleEnum.USER);
    }

    @BeforeEach
    void setUp() {
        testCardId = 1L;
        testUserId = 2L;
        testCommentId = 3L;
        testUser = testUser();
        testCard = testCard();

        commentRequestDto = new CommentRequestDto();
        commentRequestDto.setComment("comment");

        testComment = new Comment(commentRequestDto.getComment(), testUserId,
            testCardId, "nickname");
        ReflectionTestUtils.setField(testComment, "commentId", testCommentId);
    }

    /*
     * 댓글 생성 실패 테스트코드의 예외 사항은
     * cardService, userService 의 메서드 임으로
     * CommentService 에서 test 하지 않음
     * */
    @Nested
    class CreateCommentTest {

        @Test
        @DisplayName("댓글 생성 성공 테스트")
        void createCommentTest() {

            CommentRequestDto commentRequestDto = new CommentRequestDto();
            commentRequestDto.setComment("comment");

            given(cardService.findCard(testCardId)).willReturn(testCard);
            given(userService.findNickname(testUserId)).willReturn("nickname");

            commentService.createComment(testCardId, commentRequestDto, testUser);

            then(commentRepository).should(times(1)).save(any(Comment.class));
        }
    }

    @Nested
    class UpdateCommentTest {

        @Test
        @DisplayName("전달받은 각 id에 해당하는 댓글이 존재하지 않을 경우, 댓글 수정 실패 테스트")
        void updateCommentFailureTest() {

            Long notExistCommentId = 123L;

            CommentRequestDto commentRequestDto = new CommentRequestDto();
            commentRequestDto.setComment("update comment");

            assertThrows(CommentNotFoundException.class, () -> {
                commentService.updateComment(testCardId, notExistCommentId, testUserId,
                    commentRequestDto);
            });
        }

        @Test
        @DisplayName("댓글 수정 성공 테스트")
        void updateCommentSuccessTest() {

            CommentRequestDto commentRequestDto = new CommentRequestDto();
            commentRequestDto.setComment("update comment");

            given(commentRepository.findByCardIdAndCommentIdAndUserId(anyLong(), anyLong(),
                anyLong())).willReturn(Optional.of(testComment));

            commentService.updateComment(testCardId, testCommentId, testUserId, commentRequestDto);

            assertEquals(commentRequestDto.getComment(), testComment.getComment());
        }
    }

    @Nested
    class softDeleteCommentTest {

        @Test
        @DisplayName("댓글의 DeletionStatus 값이 Y로 변경될 경우, 댓글 삭제 성공 테스트")
        void softDeleteCommentSuccessTest() {

            ReflectionTestUtils.setField(testComment, "deletionStatus", DeletionStatus.N);

            given(commentRepository.findByCardIdAndCommentIdAndUserId(anyLong(), anyLong(),
                anyLong())).willReturn(Optional.of(testComment));

            commentService.deleteComment(testCardId, testCommentId, testUserId);

            assertEquals(testComment.getDeletionStatus(), DeletionStatus.Y);
        }
    }

    @Nested
    class getCommentByCardIdTest {

        @Test
        @DisplayName("해당 카드의 id로 댓글 전체 페이지 조회")
        void getCommentsByCardIdTest() {
            int page = 0;
            int size = 10;
            List<Comment> comments = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Comment comment = new Comment("comment" + i, testUserId, testCardId, "nickname");
                comments.add(comment);
            }
            Page<Comment> commentsPage = new PageImpl<>(comments);

            given(
                commentRepository.findByCardId(testCardId, PageRequest.of(page, size))).willReturn(
                commentsPage);

            Page<CommentResponseDto> resultPage = commentService.getCommentsByCardId(testCardId,
                page, size);

            assertEquals(size, resultPage.getContent().size());
        }
    }

    @Nested
    class FindLatestComment {

        @Test
        @DisplayName("존재하지 않는 cardId 인 경우, 댓글 찾기 실패 테스트")
        void cardIdNotFoundTest() {

            Long notExistCardId = 123L;

            assertThrows(IllegalArgumentException.class, () -> {
                commentService.findLatestComment(notExistCardId);
            });
        }

        @Test
        @DisplayName("가장 최신에 생성된 댓글 찾기 성공 테스트")
        void findLatestCommentTest() {

            given(commentRepository.findFirstByCardIdOrderByCreateAtDesc(anyLong())).willReturn(
                Optional.of(testComment));

            Comment result = commentService.findLatestComment(testCardId);

            assertEquals(testComment.getComment(), result.getComment());
        }
    }
}
