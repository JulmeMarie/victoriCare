package com.victoricare.api.services;

import java.util.List;

import com.victoricare.api.dtos.ContactDTO;
import com.victoricare.api.entities.User;
import com.victoricare.api.models.ContactModel;

public interface IContactService {

	ContactModel create(ContactDTO contactReq);

	ContactModel update(User connection, ContactDTO contactReq);

	void delete(User connection, Integer contactId);

	ContactModel unique(Integer contactId);

	List<ContactModel> list();
}
