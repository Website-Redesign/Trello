package com.example.trello.global.exception;

import com.example.trello.global.exception.customException.DuplicateUserInfoException;
import com.example.trello.global.exception.customException.IncorrectPasswordException;
import com.example.trello.global.exception.customException.NoColumnException;
import com.example.trello.global.exception.customException.NoEntityException;
import com.example.trello.global.exception.customException.NoPermissionException;
import com.example.trello.global.exception.customException.UserAlreadyRegisteredException;
import jakarta.websocket.DecodeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({CommentNotFoundException.class,IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(
        Exception e) {
        log.error(e.getMessage());
        return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({NoEntityException.class, NoColumnException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(
        Exception e) {
        log.error(e.getMessage());
        return createResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({NoPermissionException.class})
    public ResponseEntity<ErrorResponse> handleForbiddenException(
        Exception e) {
        log.error(e.getMessage());
        return createResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler({UserAlreadyRegisteredException.class, DecodeException.class,
        DuplicateUserInfoException.class})
    public ResponseEntity<ErrorResponse> handleConflictException(
        Exception e) {
        log.error(e.getMessage());
        return createResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler({IncorrectPasswordException.class})
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
        Exception e) {
        log.error(e.getMessage());
        return createResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    private ResponseEntity<ErrorResponse> createResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status.value())
            .body(ErrorResponse.builder()
                .state(status)
                .message(message)
                .build());
    }
}
