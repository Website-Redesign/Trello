package com.example.trello.user.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.trello.domain.user.controller.UserController;
import com.example.trello.domain.user.dto.ChangePasswordRequestDto;
import com.example.trello.domain.user.dto.SignupRequestDto;
import com.example.trello.domain.user.dto.UserDeleteRequestDto;
import com.example.trello.domain.user.dto.UserInfoRequestDto;
import com.example.trello.domain.user.entity.User;
import com.example.trello.domain.user.entity.UserRoleEnum;
import com.example.trello.domain.user.service.UserService;
import com.example.trello.global.config.WebSecurityConfig;
import com.example.trello.global.security.CustomAuthentication;
import com.example.trello.global.security.CustomAuthenticationToken;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
	controllers = UserController.class,
	excludeFilters = {
		@ComponentScan.Filter(
			type = FilterType.ASSIGNABLE_TYPE,
			classes = WebSecurityConfig.class
		)
	}
)
class UserControllerTest {

	private MockMvc mvc;

	private Principal mockPrincipal;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

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
	@DisplayName("회원가입 테스트")
	void signup() throws Exception {
		//given
		SignupRequestDto requestDto = new SignupRequestDto();
		requestDto.setEmail("test@naver.com");
		requestDto.setPassword("12345678");
		requestDto.setNickname("test");
		requestDto.setIntroduction("설명");
		requestDto.setPhoto("사진url");

		String requestBody = objectMapper.writeValueAsString(requestDto);
		//when - then
		mvc.perform(post("/users/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isOk());

	}

	@Test
	@DisplayName("유저 정보 갱신 테스트")
	void updateUser() throws Exception {
		//given
		UserInfoRequestDto requestDto = new UserInfoRequestDto();
		requestDto.setNickname("테스터");
		requestDto.setIntroduction("새로운설명");
		requestDto.setPhoto("새로운 사진");
		this.testUser();
		String requestBody = objectMapper.writeValueAsString(requestDto);
		//when - then
		mvc.perform(patch("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal)
				.content(requestBody))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("비밀번호 변경 테스트")
	void changePasswordTest() throws Exception {
		//given
		ChangePasswordRequestDto requestDto = new ChangePasswordRequestDto();
		requestDto.setExistingPassword("12345678");
		requestDto.setNewPassword("test123456");
		this.testUser();
		String requestBody = objectMapper.writeValueAsString(requestDto);
		//when - then
		mvc.perform(patch("/users/change-password")
				.contentType(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal)
				.content(requestBody))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("회원탈퇴 테스트")
	void deleteUserTest() throws Exception {
		//given
		UserDeleteRequestDto requestDto = new UserDeleteRequestDto();
		requestDto.setPassword("12345678");
		this.testUser();
		String requestBody = objectMapper.writeValueAsString(requestDto);
		//when - then
		mvc.perform(delete("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal)
				.content(requestBody))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("특정회원 정보보기 테스트")
	void getUserTest() throws Exception {
		//given
		//when - then
		mvc.perform(get("/users/1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("전체회원 정보보기 테스트")
	void getAllUserTest() throws Exception {
		//given
		//when - then
		mvc.perform(get("/users")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("나의 정보보기 테스트")
	void getMyPageTest() throws Exception {
		//given
		this.testUser();
		//when - then
		mvc.perform(get("/users/my-page")
				.contentType(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal))
			.andExpect(status().isOk());
	}
}
