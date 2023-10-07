package com.victoricare.api.services.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victoricare.api.dtos.ContactDTO;
import com.victoricare.api.entities.Contact;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.models.ContactModel;
import com.victoricare.api.repositories.ContactRepository;
import com.victoricare.api.security.jwt.JWTUtils;
import com.victoricare.api.services.IContactMailService;
import com.victoricare.api.services.IContactService;
import com.victoricare.api.services.IFileService;
import com.victoricare.api.services.IUserService;

@Service
public class ContactServiceimpl implements IContactService{

	private static final Logger logger = LoggerFactory.getLogger(ContactServiceimpl.class);

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private IContactMailService contactMailService;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private IFileService fileService;

	@Override
	public ContactModel create(ContactDTO appRequest) {
		Contact contact = new Contact();
		contact.setCreateAtContact(Date.from(Instant.now()));
		contact.setEmailContact(appRequest.getEmail());
		contact.setNameContact(appRequest.getName());
		contact.setSubjectContact(appRequest.getSubject());
		contact.setTextContact(appRequest.getText());

		List<String>contactAddressees = this.userService.findAllContactAddressees().get().stream().map(user->user.getEmailUser()).collect(Collectors.toList());

		this.contactMailService.mailForContactUs(contactAddressees, contact);
		this.contactRepository.save(contact);
		
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return ContactModel.newInstance().init(fileService, contact);
	}

	@Override
	public ContactModel update(User admin, ContactDTO contactReq) {
		Optional<Contact> contactOpt = this.contactRepository.findById(contactReq.getId());
		if(contactOpt.isEmpty()) {
			throw new ResourceNotFoundException();
		}

		Contact contact = contactOpt.get();
		contact.setResponseContact(contactReq.getResponse());
		contact.setResponseAtContact(Date.from(Instant.now()));
		contact.setResponseByContact(admin);
		this.contactRepository.save(contact);
		this.contactMailService.mailForResponse(contact);
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return ContactModel.newInstance().init(fileService, contact);
	}

	@Override
	public void delete(User admin, Integer contactId) {
		Contact contact =  this.contactRepository.findNonDeletedById(contactId).orElseThrow(()-> new ResourceNotFoundException());
		contact.setDeleteAtContact(Date.from(Instant.now()));
		contact.setDeleteByContact(admin);
		this.contactRepository.save(contact);
	}

	@Override
	public ContactModel unique(Integer contactId) {
		Contact contact = this.contactRepository.findNonDeletedById(contactId).orElseThrow(() -> {
			logger.error("carousel item not found for id : {}", contactId);
			throw new ResourceNotFoundException();
		});
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return ContactModel.newInstance().init(fileService ,contact);
	}

	@Override
	public List<ContactModel> list() {
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		List<Contact> contactList = this.contactRepository.findNonDeletedAll();
		return contactList
			.stream()
			.map(contact -> ContactModel.newInstance().init(fileService ,contact))
			.collect(Collectors.toList());
	}
}
