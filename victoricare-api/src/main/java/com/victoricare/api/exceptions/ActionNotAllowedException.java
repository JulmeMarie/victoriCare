package com.victoricare.api.exceptions;

import com.victoricare.api.enums.EMessage;

public class ActionNotAllowedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ActionNotAllowedException(EMessage message) {
		super(message.name());
	}
}
