package com.victoricare.api.exceptions;

public class TokenException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public TokenException() {
		super("token-exception");
	}
}
