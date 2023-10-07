package com.victoricare.api.exceptions;

public class UnknownException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UnknownException() {
		super("unknown-exception");
	}
}
