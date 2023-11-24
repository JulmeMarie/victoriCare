package com.victoricare.api.exceptions;

import com.victoricare.api.enums.EMessage;

public class ResourceNotModifiedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotModifiedException(EMessage message) {
		super(message.name());
	}
}
