package com.victoricare.api.exceptions;

import com.victoricare.api.enums.EMessage;

public class ActionForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ActionForbiddenException(EMessage message) {
		super(message.name());
	}
}
