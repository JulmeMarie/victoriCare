package com.victoricare.api.exceptions;

import com.victoricare.api.enums.EMessage;

public class InvalidInputException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidInputException(EMessage message) {
		super(message.name());
	}
}
