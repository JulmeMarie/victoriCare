package com.victoricare.api.services.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.victoricare.api.entities.User;
import com.victoricare.api.services.IUserMailService;

@Service
public class UserMailServiceImpl extends MailServiceImpl implements IUserMailService{

	@Override
	public void mailForAccountCreation(User user) {
		this.details.setSubject("Création de compte");
		this.details.setTo(List.of(user.getEmailUser()));
		this.details.getProperties().put("userId", user.getIdUser());
		this.details.getProperties().put("userFirstname", user.getFirstnameUser());
		this.details.getProperties().put("userLastname", user.getLastnameUser());
		this.details.getProperties().put("userCreateAt", user.getCreateAtUser());
		this.details.getProperties().put("userAccountCode", user.getAccountCodeUser());

		this.details.getProperties().put("authorFirstname", user.getCreateByUser().getFirstnameUser());
		this.details.getProperties().put("authorLastname", user.getCreateByUser().getLastnameUser());
		this.details.getProperties().put("type", "creation");
		this.details.getProperties().put("destination", "user");

		this.details.setTemplate("account-creation");
		this.sendHtmlMessage();

		this.details.getProperties().put("destination", "author");
		this.details.setTo(List.of(user.getCreateByUser().getEmailUser()));
		this.details.setTemplate("account-creation");
		this.sendHtmlMessage();
	}

	@Override
	public void mailForAccountUpdate(User user) {
		this.details.setSubject("Mise à jour de compte");
		this.details.setTo(List.of(user.getEmailUser()));
		this.details.getProperties().put("userId", user.getIdUser());
		this.details.getProperties().put("userFirstname", user.getFirstnameUser());
		this.details.getProperties().put("userLastname", user.getLastnameUser());
		this.details.getProperties().put("authorFirstname", user.getUpdateByUser().getFirstnameUser());
		this.details.getProperties().put("authorLastname", user.getUpdateByUser().getLastnameUser());
		this.details.getProperties().put("destination", "user");
		this.details.setTemplate("account-update");
		this.sendHtmlMessage();

		this.details.getProperties().put("destination", "author");
		this.details.setTo(List.of(user.getUpdateByUser().getEmailUser()));
		this.details.setTemplate("account-update");
		this.sendHtmlMessage();
	}

	@Override
	public void mailForRecoverAccount(User author) {
		this.details.setSubject("Récupération de compte");
		this.details.setTo(List.of(author.getEmailUser()));
		this.details.getProperties().put("userFirstname", author.getFirstnameUser());
		this.details.getProperties().put("userLastname", author.getLastnameUser());
		this.details.getProperties().put("userId", author.getIdUser());
		this.details.getProperties().put("type", "recovery");
		this.details.getProperties().put("subject", "récupération de compte");
		this.details.getProperties().put("userCodeAt", author.getAccountCodeAtUser());
		this.details.getProperties().put("userCode", author.getAccountCodeUser());
		this.details.setTemplate("content-update");
		this.sendHtmlMessage();
	}

	@Override
	public void mailForUpdatePassword(User author) {
		this.details.setSubject("Mise à jour de mot de passe");
		this.details.setTo(List.of(author.getEmailUser()));
		this.details.getProperties().put("userFirstname", author.getFirstnameUser());
		this.details.getProperties().put("userLastname", author.getLastnameUser());
		this.details.getProperties().put("userId", author.getIdUser());
		this.details.getProperties().put("type", "password");
		this.details.getProperties().put("subject", "mise à jour de mot de passe");
		this.details.getProperties().put("userCodeAt", author.getEmailCodeAtUser());
		this.details.getProperties().put("userCode", author.getEmailCodeUser());
		this.details.setTemplate("content-update");
		this.sendHtmlMessage();
	}

	@Override
	public void mailForUpdateEmail(User author) {

		this.details.setSubject("Mise à jour d'adresse e-mail");
		this.details.setTo(List.of(author.getEmailNewUser()));
		this.details.getProperties().put("userFirstname", author.getFirstnameUser());
		this.details.getProperties().put("userLastname", author.getLastnameUser());
		this.details.getProperties().put("userId", author.getIdUser());
		this.details.getProperties().put("type", "email");
		this.details.getProperties().put("subject", "mise à jour d'adresse e-mail");
		this.details.getProperties().put("userCodeAt", author.getEmailCodeAtUser());
		this.details.setTemplate("content-update");
		this.sendHtmlMessage();

		this.details.setTo(List.of(author.getEmailUser()));
		this.details.getProperties().put("userCode", author.getEmailCodeUser());
		this.sendHtmlMessage();
	}
}
