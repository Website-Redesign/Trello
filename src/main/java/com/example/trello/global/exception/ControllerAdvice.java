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

@Slf4j
@AllArgsConstructor
public class ControllerAdvice {

    @ExceptionHandler({CommentNotFoundException.class,IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(
        CommentNotFoundException e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({NoEntityException.class, NoColumnException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(
        CommentNotFoundException e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler({NoPermissionException.class})
    public ResponseEntity<ErrorResponse> handleForbiddenException(
        CommentNotFoundException e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler({UserAlreadyRegisteredException.class, DecodeException.class,
        DuplicateUserInfoException.class})
    public ResponseEntity<ErrorResponse> handleConflictException(
        CommentNotFoundException e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler({IncorrectPasswordException.class})
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
        CommentNotFoundException e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
