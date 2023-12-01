package com.victoricare.api.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ResourceAlreadyExistsException() {
		super("already-exist-exception");
	}
}
