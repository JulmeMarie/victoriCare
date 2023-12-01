package com.victoricare.api.exceptions;

public class UserException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UserException() {
		super("user-exception");
	}
}
