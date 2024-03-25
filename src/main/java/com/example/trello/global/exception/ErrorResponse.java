package com.example.trello.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {

    private String message;

    private HttpStatus state;

}
