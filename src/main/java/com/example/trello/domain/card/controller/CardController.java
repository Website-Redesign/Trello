package com.example.trello.domain.card.controller;

import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.domain.card.dto.CardResponseDto;
import com.example.trello.domain.card.repository.CardRepositoryCustom;
import com.example.trello.domain.card.service.CardService;
import com.example.trello.domain.user.dto.UserResponseDto;
import com.example.trello.global.dto.CommonResponseDto;
import com.example.trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class CardController {

	private final CardService cardService;

	@PostMapping("/columns/{columnsId}/cards")
	public ResponseEntity<CommonResponseDto<Void>> createCard(
		@PathVariable Long columnsId,@AuthenticationPrincipal UserDetailsImpl userDetails,
		CardRequestDto requestDto){
		cardService.createCard(columnsId,userDetails.getUser().getId(), requestDto);
		return ResponseEntity.ok()
			.body(CommonResponseDto.<Void>builder().build());
	}

	@PatchMapping("/columns/cards/{cardsId}")
	public ResponseEntity<CommonResponseDto<Void>> updateCard(
		@PathVariable Long cardsId,@AuthenticationPrincipal UserDetailsImpl userDetails,
		CardRequestDto requestDto){
		cardService.updateCard(cardsId,userDetails.getUser().getId(), requestDto);
		return ResponseEntity.ok()
			.body(CommonResponseDto.<Void>builder().build());
	}

	@DeleteMapping("/columns/cards/{cardsId}")
	public ResponseEntity<CommonResponseDto<Void>> deleteCard(
		@PathVariable Long cardsId,@AuthenticationPrincipal UserDetailsImpl userDetails){
		cardService.deleteCard(cardsId,userDetails.getUser().getId());
		return ResponseEntity.ok()
			.body(CommonResponseDto.<Void>builder().build());
	}

	@GetMapping("/columns/cards/{cardsId}")
	public ResponseEntity<CommonResponseDto<CardResponseDto>> getCard(
		@PathVariable Long cardsId,@AuthenticationPrincipal UserDetailsImpl userDetails){
		CardResponseDto responseDto = cardService.getCard(cardsId,userDetails.getUser().getId());
		return ResponseEntity.ok()
			.body(CommonResponseDto.<CardResponseDto>builder()
				.date(responseDto)
				.build());
	}
}
