package com.victoricare.api.exceptions;

import com.victoricare.api.enums.EMessage;

public class ActionNotAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ActionNotAuthorizedException(EMessage message) {
		super(message.name());
	}
}
