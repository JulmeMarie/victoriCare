package com.victoricare.api.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super("not-found-exception");
	}
}
