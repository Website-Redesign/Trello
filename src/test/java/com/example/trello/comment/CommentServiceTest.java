package com.example.trello.comment;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.entity.Card;
import com.example.trello.domain.card.service.CardService;
import com.example.trello.domain.comment.dto.CommentRequestDto;
import com.example.trello.domain.comment.entity.Comment;
import com.example.trello.domain.comment.repository.CommentRepository;
import com.example.trello.domain.comment.service.CommentService;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.entity.UserRoleEnum;
import com.example.trello.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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
    }

    /*
     * 댓글 생성 실패 테스트코드의 예외 사항은
     * cardService, userService 의 메서드 임으로
     * CommentService 에서 test 하지 않음
     * */
    @Nested
    class FindCommentTest {

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
}
