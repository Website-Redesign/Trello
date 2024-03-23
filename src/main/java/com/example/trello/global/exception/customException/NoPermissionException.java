package com.example.trello.global.exception.customException;

public class NoPermissionException extends RuntimeException {
	public NoPermissionException(String message) {
		super(message);
	}
}
