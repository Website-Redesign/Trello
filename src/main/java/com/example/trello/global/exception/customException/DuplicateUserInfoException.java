package com.example.trello.global.exception.customException;

public class DuplicateUserInfoException extends RuntimeException {
	public DuplicateUserInfoException(String message){
		super(message);
	}
}
