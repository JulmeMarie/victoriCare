package com.victoricare.api.exceptions;

public class MailException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public MailException() {
		super("can not send mail");
	}
}