package com.victoricare.api.exceptions;

import com.victoricare.api.enums.EMessage;

public class InternalServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InternalServerException(EMessage message) {
		super(message.name());
	}
}
