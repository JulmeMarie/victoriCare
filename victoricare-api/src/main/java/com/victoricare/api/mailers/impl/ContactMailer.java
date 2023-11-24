package com.victoricare.api.mailers.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.victoricare.api.entities.Contact;
import com.victoricare.api.entities.User;
import com.victoricare.api.mailers.IContactMailer;
import com.victoricare.api.services.IUserService;

@Service
public class ContactMailer extends Mailer implements IContactMailer {

	@Autowired
	private IUserService userService;

	@Override
	public void mailForContactUs(Contact contact) {
		List<User> users = userService.getAllAdmins();
		this.mail.setSubject(contact.getSubject());
		this.mail.setTo(users.stream().map(user -> user.getEmail()).toList());
		this.mail.getProperties().put("name", contact.getName());
		this.mail.getProperties().put("text", contact.getText());
		this.mail.setTemplate("contact-request");
		this.sendHtmlMessage();
	}

	@Override
	public void mailForResponse(Contact contact) {
		this.mail.setSubject("Réponse : " + contact.getSubject());
		this.mail.setTo(List.of(contact.getEmail()));
		this.mail.getProperties().put("name", contact.getName());
		this.mail.getProperties().put("text", contact.getText());
		this.mail.getProperties().put("response", contact.getResponse());
		this.mail.getProperties().put("authorFirstName",
				contact.getResponseAuthor().getFirstname());
		this.mail.getProperties().put("authorLastName", contact.getResponseAuthor().getLastname());
		this.mail.getProperties().put("date", contact.getCreationDate());
		this.mail.setTemplate("contact-response");
		this.sendHtmlMessage();
	}
}
