package com.victoricare.api.mailers.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import com.victoricare.api.entities.User;
import com.victoricare.api.mailers.IUserMailer;

@Service
public class UserMailer extends Mailer implements IUserMailer {

	@Override
	public void mailForAccountCreation(User user) {
		this.mail.setSubject("Création de compte");
		this.mail.setTo(List.of(user.getEmail()));
		this.mail.getProperties().put("userId", user.getId());
		this.mail.getProperties().put("userFirstname", user.getFirstname());
		this.mail.getProperties().put("userLastname", user.getLastname());
		this.mail.getProperties().put("userCreateAt", user.getCreationDate());
		// this.details.getProperties().put("userAccountCode", user.get);

		this.mail.getProperties().put("authorFirstname", user.getFirstname());
		this.mail.getProperties().put("authorLastname", user.getLastname());
		this.mail.getProperties().put("type", "creation");
		this.mail.getProperties().put("destination", "user");

		this.mail.setTemplate("account-creation");
		this.sendHtmlMessage();

		this.mail.getProperties().put("destination", "author");
		// this.details.setTo(List.of(user.getCreateBy().getEmail()));
		this.mail.setTemplate("account-creation");
		this.sendHtmlMessage();
	}

	@Override
	public void mailForAccountUpdate(User user) {
		this.mail.setSubject("Mise à jour de compte");
		this.mail.setTo(List.of(user.getEmail()));
		this.mail.getProperties().put("userId", user.getId());
		this.mail.getProperties().put("userFirstname", user.getFirstname());
		// this.details.getProperties().put("userLastname", user.getLastname());
		// this.details.getProperties().put("authorFirstname",
		// user.getUpdateBy().getFirstname());
		// this.details.getProperties().put("authorLastname",
		// user.getUpdateBy().getLastname());
		this.mail.getProperties().put("destination", "user");
		this.mail.setTemplate("account-update");
		this.sendHtmlMessage();

		this.mail.getProperties().put("destination", "author");
		// this.details.setTo(List.of(user.getUpdateBy().getEmail()));
		this.mail.setTemplate("account-update");
		this.sendHtmlMessage();
	}

	@Override
	public void mailForRecoverAccount(User author) {
		this.mail.setSubject("Récupération de compte");
		this.mail.setTo(List.of(author.getEmail()));
		this.mail.getProperties().put("userFirstname", author.getFirstname());
		// this.details.getProperties().put("userLastname", author.getLastname());
		this.mail.getProperties().put("userId", author.getId());
		this.mail.getProperties().put("type", "recovery");
		this.mail.getProperties().put("subject", "récupération de compte");
		// this.details.getProperties().put("userCodeAt", author.getAccountCodeAt());
		// this.details.getProperties().put("userCode", author.getAccountCode());
		this.mail.setTemplate("content-update");
		this.sendHtmlMessage();
	}

	@Override
	public void mailForUpdatePassword(User author) {
		this.mail.setSubject("Mise à jour de mot de passe");
		this.mail.setTo(List.of(author.getEmail()));
		this.mail.getProperties().put("userFirstname", author.getFirstname());
		// this.details.getProperties().put("userLastname", author.getLastname());
		this.mail.getProperties().put("userId", author.getId());
		this.mail.getProperties().put("type", "password");
		this.mail.getProperties().put("subject", "mise à jour de mot de passe");
		// this.details.getProperties().put("userCodeAt", author.getEmailCodeAt());
		// this.details.getProperties().put("userCode", author.getEmailCode());
		this.mail.setTemplate("content-update");
		this.sendHtmlMessage();
	}

	@Override
	public void mailForUpdateEmail(User author) {

		this.mail.setSubject("Mise à jour d'adresse e-mail");
		// this.details.setTo(List.of(author.getEmailNew()));
		this.mail.getProperties().put("userFirstname", author.getFirstname());
		// this.details.getProperties().put("userLastname", author.getLastname());
		this.mail.getProperties().put("userId", author.getId());
		this.mail.getProperties().put("type", "email");
		this.mail.getProperties().put("subject", "mise à jour d'adresse e-mail");
		// this.details.getProperties().put("userCodeAt", author.getEmailCodeAt());
		this.mail.setTemplate("content-update");
		this.sendHtmlMessage();

		this.mail.setTo(List.of(author.getEmail()));
		// this.details.getProperties().put("userCode", author.getEmailCode());
		this.sendHtmlMessage();
	}
}
