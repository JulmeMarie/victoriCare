package com.victoricare.api.exceptions;

public class ResourceNotUpdatedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceNotUpdatedException() {
		super("not-update-exception");
	}

}
