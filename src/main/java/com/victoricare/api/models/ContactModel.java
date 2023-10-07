package com.victoricare.api.models;

import java.util.Date;

import com.victoricare.api.entities.Contact;
import com.victoricare.api.services.IFileService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactModel {

    private Integer id;

    private String name;

	private String email;

	private String text;

	private String subject;

	private String response;

	private Date createAt;

	private Date responseAt;

	private Date deleteAt;

	private UserModel responseBy;

	private UserModel deleteBy;

	public static ContactModel newInstance() {
		return new ContactModel();
	}

	public ContactModel init(IFileService fileService, Contact contact) {
		if( contact == null ) {
			return null;
		}
	    this.id = contact.getIdContact();
	    this.name = contact.getNameContact();
	    this.email = contact.getEmailContact();
	    this.text = contact.getTextContact();
	    this.response = contact.getResponseContact();
	    this.subject = contact.getSubjectContact();
	    this.createAt = contact.getCreateAtContact();
	    this.deleteAt = contact.getDeleteAtContact();
	    this.responseAt = contact.getResponseAtContact();
	    this.responseBy = UserModel.newInstance().init(fileService, contact.getResponseByContact());
		this.deleteBy = UserModel.newInstance().init(fileService, contact.getDeleteByContact());
		return this;
	}
}
