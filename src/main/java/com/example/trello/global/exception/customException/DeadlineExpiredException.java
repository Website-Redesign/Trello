package com.example.trello.global.exception.customException;


public class DeadlineExpiredException extends RuntimeException {
	public DeadlineExpiredException(String message) {
		super(message);
	}
}
