package com.example.trello.global.exception.customException;

public class IncorrectPasswordException extends RuntimeException {
	public IncorrectPasswordException(String message) {
		super(message);
	}
}
