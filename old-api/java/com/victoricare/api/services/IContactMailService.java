package com.victoricare.api.services;

import java.util.List;

import com.victoricare.api.entities.Contact;

public interface IContactMailService{

	void mailForContactUs(List<String> contactAddresses, Contact contact);

	void mailForResponse(Contact contact);

}
