package com.example.trello.card.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.trello.domain.card.controller.CardController;
import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.service.CardService;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.entity.UserRoleEnum;
import com.example.trello.global.config.WebSecurityConfig;
import com.example.trello.global.security.CustomAuthentication;
import com.example.trello.global.security.UserDetailsImpl;
import com.example.trello.global.util.JwtUtil;
import com.example.trello.mvc.MockSpringSecurityFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
	controllers = CardController.class,
	excludeFilters = {
		@ComponentScan.Filter(
			type = FilterType.ASSIGNABLE_TYPE,
			classes = WebSecurityConfig.class
		)
	}
)
public class CardControllerTest {

	private MockMvc mvc;

	private Principal mockPrincipal;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CardService cardService;

	@MockBean
	JwtUtil jwtUtil;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context)
			.apply(springSecurity(new MockSpringSecurityFilter()))
			.build();
	}

	private void testUser() {
		String email = "test@gmail.com";
		Long userId = 5L;
		UserRoleEnum role = UserRoleEnum.USER;
		User user = new User(userId, email, role);
		UserDetailsImpl testUserDetails = new UserDetailsImpl(user);
		mockPrincipal = new CustomAuthentication(testUserDetails);
	}

	@Test
	@DisplayName("카드 생성 테스트")
	void createCard() throws Exception {
		//given
		CardRequestDto requestDto = new CardRequestDto();
		requestDto.setCardname("카드명");
		requestDto.setColor("파란색");
		requestDto.setDescription("설명들");
		this.testUser();

		String requestBody = objectMapper.writeValueAsString(requestDto);
		//when - then
		mvc.perform(post("/boards/columns/1/cards")
				.contentType(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal)
				.content(requestBody))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("카드 수정 테스트")
	void updateCard() throws Exception {
		//given
		CardRequestDto requestDto = new CardRequestDto();
		requestDto.setCardname("카드명");
		requestDto.setColor("파란색");
		requestDto.setDescription("설명들");
		this.testUser();

		String requestBody = objectMapper.writeValueAsString(requestDto);
		//when - then
		mvc.perform(patch("/boards/columns/cards/1")
				.contentType(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal)
				.content(requestBody))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("카드 삭제 테스트")
	void deleteCard() throws Exception {
		//given
		this.testUser();

		//when - then
		mvc.perform(delete("/boards/columns/cards/1")
				.contentType(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("카드 정보보기 테스트")
	void getCard() throws Exception {
		//given
		this.testUser();

		//when - then
		mvc.perform(get("/boards/columns/cards/1")
				.contentType(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal))
			.andExpect(status().isOk());
	}
}
