package com.victoricare.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.victoricare.api.dtos.ContactDTO;
import com.victoricare.api.models.ContactModel;
import com.victoricare.api.security.jwt.AuthTokenFilter;
import com.victoricare.api.services.IContactService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
@RestController
public class ContactController {

	@Autowired
	public IContactService contactService;

	@PutMapping(path = "/api/right-anm/contact", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContactModel> create(@RequestBody ContactDTO contactReq) {
		return ResponseEntity.ok(contactService.create(contactReq));
	}

	@PostMapping(path = "/api/right-adm/contact", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContactModel> update(Authentication authentication, @RequestBody ContactDTO contactReq) {
		return ResponseEntity.ok(contactService.update(AuthTokenFilter.getOnlineUser(authentication), contactReq));
	}

	@DeleteMapping(path = "/api/right-adm/contacts/{contactId}")
	public void delete(Authentication authentication, @PathVariable Integer contactId) {
		contactService.delete(AuthTokenFilter.getOnlineUser(authentication), contactId);
	}

	@GetMapping(path = "/api/right-adm/contacts/{contactId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContactModel> unique(@PathVariable Integer contactId) {
		return ResponseEntity.ok(contactService.unique(contactId));
	}

	@GetMapping(path = "/api/right-adm/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContactModel>> list() {
		return ResponseEntity.ok(contactService.list());
	}
}
