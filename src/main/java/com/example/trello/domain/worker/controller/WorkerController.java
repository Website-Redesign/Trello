package com.example.trello.domain.worker.controller;

import com.example.trello.domain.worker.service.WorkerService;
import com.example.trello.global.dto.CommonResponseDto;
import com.example.trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/columns")
public class WorkerController {

	private final WorkerService workerService;

	@PostMapping("/cards/{cardId}/worker")
	public ResponseEntity<CommonResponseDto<Void>> createWorker(@PathVariable Long cardId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		workerService.createWorker(cardId, userDetails.getUser().getId());
		return ResponseEntity.ok()
			.body(CommonResponseDto.<Void>builder().build());
	}

	@DeleteMapping("/cards/{cardId}/worker")
	public ResponseEntity<CommonResponseDto<Void>> deleteWorker(@PathVariable Long cardId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		workerService.deleteWorker(cardId, userDetails.getUser().getId());
		return ResponseEntity.ok()
			.body(CommonResponseDto.<Void>builder().build());
	}

}
