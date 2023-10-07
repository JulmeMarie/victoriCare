package com.victoricare.api.exceptions;

public class AuthException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AuthException() {
		super("auth-exception");
	}
}
