package com.victoricare.api.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.victoricare.api.entities.Contact;
import com.victoricare.api.services.IContactMailService;

@Service
public class ContactMailServiceImpl extends MailServiceImpl implements IContactMailService{


	@Override
	public void mailForContactUs(List<String> contactAddresses, Contact contact) {
		this.details.setSubject(contact.getSubjectContact());
		this.details.setTo(contactAddresses);
		this.details.getProperties().put("name", contact.getNameContact());
		this.details.getProperties().put("text", contact.getTextContact());
		this.details.setTemplate("contact-request");
		this.sendHtmlMessage();
	}

	@Override
	public void mailForResponse(Contact contact) {
		this.details.setSubject("Réponse : " + contact.getSubjectContact());
		this.details.setTo(List.of(contact.getEmailContact()));
		this.details.getProperties().put("name", contact.getNameContact());
		this.details.getProperties().put("text", contact.getTextContact());
		this.details.getProperties().put("response", contact.getResponseContact());
		this.details.getProperties().put("authorFirstName", contact.getResponseByContact().getFirstnameUser());
		this.details.getProperties().put("authorLastName", contact.getResponseByContact().getLastnameUser());
		this.details.getProperties().put("date", contact.getCreateAtContact());
		this.details.setTemplate("contact-response");
		this.sendHtmlMessage();
	}
}
