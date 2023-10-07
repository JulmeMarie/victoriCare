package com.victoricare.api.exceptions;

public class ResourceNotAllowedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceNotAllowedException() {
		super("not-allowed-exception");
	}
}