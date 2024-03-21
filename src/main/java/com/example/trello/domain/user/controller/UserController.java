package com.example.trello.domain.user.controller;

import com.example.trello.domain.user.dto.ChangePasswordRequestDto;
import com.example.trello.domain.user.dto.SignupRequestDto;
import com.example.trello.domain.user.dto.UserDeleteRequestDto;
import com.example.trello.domain.user.dto.UserInfoRequestDto;
import com.example.trello.domain.user.dto.UserResponseDto;
import com.example.trello.domain.user.service.UserService;
import com.example.trello.global.dto.CommonResponseDto;
import com.example.trello.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/users/signup")
	public ResponseEntity<CommonResponseDto<Void>> signup(
		@Valid @RequestBody SignupRequestDto requestDto){
		userService.signup(requestDto);
		return ResponseEntity.ok()
			.body(CommonResponseDto.<Void>builder().build());
	}

	@PatchMapping("/users")
	public ResponseEntity<CommonResponseDto<Void>> updateUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody UserInfoRequestDto requestDto){
		userService.updateUser(userDetails.getUser().getId(), requestDto);
		return ResponseEntity.ok()
			.body(CommonResponseDto.<Void>builder().build());
	}

	@PatchMapping("/users/change-password")
	public ResponseEntity<CommonResponseDto<Void>> changePassword(
		@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody ChangePasswordRequestDto requestDto){
		userService.changePassword(userDetails.getUser().getId(), requestDto);
		return ResponseEntity.ok()
			.body(CommonResponseDto.<Void>builder().build());
	}

	@DeleteMapping("/users")
	public ResponseEntity<CommonResponseDto<Void>> deleteUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody UserDeleteRequestDto requestDto,
		HttpServletRequest req){
		String token = req.getHeader("Authorization");
		userService.deleteUser(userDetails.getUser().getId(),requestDto,token);
		return ResponseEntity.ok()
			.body(CommonResponseDto.<Void>builder().build());
	}

	@GetMapping("/users/logout")
	public ResponseEntity<CommonResponseDto<Void>> logout(
		HttpServletRequest req){
		String token = req.getHeader("Authorization");
		userService.logout(token);
		return ResponseEntity.ok()
			.body(CommonResponseDto.<Void>builder().build());
	}

	@GetMapping("/users/my-page")
	public ResponseEntity<CommonResponseDto<UserResponseDto>> myInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails){
		UserResponseDto responseDto = userService.getUser(userDetails.getUser().getId());
		return ResponseEntity.ok()
			.body(CommonResponseDto.<UserResponseDto>builder()
				.date(responseDto)
				.build());
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<CommonResponseDto<UserResponseDto>> getUser(
		@PathVariable Long userId){
		UserResponseDto responseDto = userService.getUser(userId);
		return ResponseEntity.ok()
			.body(CommonResponseDto.<UserResponseDto>builder()
				.date(responseDto)
				.build());
	}

	@GetMapping("/users")
	public ResponseEntity<CommonResponseDto<Page<UserResponseDto>>> getAllUsers(){
		Page<UserResponseDto> responseDto = userService.getAllUsers();
		return ResponseEntity.ok()
			.body(CommonResponseDto.<Page<UserResponseDto>>builder()
				.date(responseDto)
				.build());
	}
}
