package com.victoricare.api.mailers;

import com.victoricare.api.entities.Contact;

public interface IContactMailer {

	void mailForContactUs(Contact contact);

	void mailForResponse(Contact contact);

}
