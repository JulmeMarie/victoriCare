package com.victoricare.api.exceptions;

import com.victoricare.api.enums.EMessage;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(EMessage message) {
		super(message.name());
	}
}
