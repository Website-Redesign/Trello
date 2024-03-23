package com.example.trello.global.exception.customException;

public class UserAlreadyRegisteredException extends RuntimeException {
	public UserAlreadyRegisteredException(String message) {
		super(message);
	}
}
