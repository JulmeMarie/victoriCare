package com.victoricare.api.mailers;

import jakarta.mail.MessagingException;

public interface IMailer {
	public void sendHtmlMessage() throws MessagingException;

}
