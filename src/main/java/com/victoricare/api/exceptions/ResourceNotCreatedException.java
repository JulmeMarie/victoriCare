package com.victoricare.api.exceptions;

public class ResourceNotCreatedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceNotCreatedException() {
		super("not-inserted-exception");
	}
}
