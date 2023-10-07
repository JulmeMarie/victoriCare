package com.victoricare.api.services;

import jakarta.mail.MessagingException;

public interface IMailService {
	public void sendHtmlMessage() throws MessagingException;

}
