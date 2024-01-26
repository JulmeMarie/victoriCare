package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.inputs.ContactInputDTO;
import com.victoricare.api.entities.Contact;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.mailers.IContactMailer;
import com.victoricare.api.repositories.IContactRepository;
import com.victoricare.api.security.JWTToken;
import com.victoricare.api.services.IContactService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContactService implements IContactService {

    @Autowired
    private IContactMailer contactMailer;

    @Autowired
    private IContactRepository repository;

    @Override
    public Contact doCreate(JWTToken jwtToken, ContactInputDTO dto) {
        Contact contact = new Contact();
        contact.setCreationDate(new Date());
        contact.setEmail(dto.getEmail());
        contact.setName(dto.getName());
        contact.setSubject(dto.getSubject());
        contact.setText(dto.getText());
        contact.setBrowser(jwtToken.getBrowser());
        contact.setIp(jwtToken.getIp());
        this.repository.save(contact);
        this.contactMailer.mailForContactUs(contact);
        return contact;
    }

    @Override
    public Contact doResponse(User onlineUser, String message, Integer id) {
        Contact contact = this.doGet(onlineUser, id);
        contact.setResponse(message);
        contact.setResponseDate(new Date());
        contact.setResponseAuthor(onlineUser);
        this.repository.save(contact);
        this.contactMailer.mailForResponse(contact);
        return contact;
    }

    @Override
    public void doDelete(User onlineUser, Integer id) {
        Contact contact = this.doGet(onlineUser, id);
        this.repository.delete(contact);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + Contact.class + " with id : [" + id + "]");
    }

    @Override
    public Contact doGet(User onlineUser, Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.CONTACT_NOT_FOUND));
    }

    @Override
    public Page<Contact> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<Contact> doListByUser(User onlineUser, Integer userId) {
        return this.repository.findAllByUser(userId);
    }
}