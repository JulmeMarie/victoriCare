package com.victoricare.api.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.victoricare.api.dtos.inputs.ContactInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.outputs.ContactOutputDTO;
import com.victoricare.api.entities.Contact;
import com.victoricare.api.mappers.ContactMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.JWTToken;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireUserRole;
import com.victoricare.api.services.IContactService;
import com.victoricare.api.validators.impl.PageValidator;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequireUserRole
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private IContactService service;

    @Autowired
    private JWTToken jwtToken;

    @RequireUserRole
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ContactOutputDTO> onCreate(Authentication authentication,
            @ModelAttribute ContactInputDTO dto) {
        jwtToken.init();
        Contact contact = service.doCreate(this.jwtToken, dto);
        return ResponseEntity.ok(ContactMapper.toOutput(contact));
    }

    @RequireUserRole
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ContactOutputDTO> onResponse(Authentication authentication,
            @RequestBody String message,
            @PathVariable Integer id) {
        Contact contact = service.doResponse(AuthFilter.getOnlineUser(authentication), message, id);
        return ResponseEntity.ok(ContactMapper.toOutput(contact));
    }

    @RequireUserRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication, @PathVariable Integer id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactOutputDTO> onGet(Authentication authentication, @PathVariable Integer id) {
        Contact contact = service.doGet(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(ContactMapper.toOutput(contact));
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ContactOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(Contact.class, dto);
        Page<Contact> page = service.doPage(pageable);
        return ResponseEntity.ok(page.map(ContactMapper::toOutput));
    }

    @RequireUserRole
    @GetMapping(path = "/by-user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContactOutputDTO>> onListByUser(Authentication authentication,
            @PathVariable Integer id) {
        List<Contact> contacts = service.doListByUser(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(contacts.stream().map(ContactMapper::toOutput).toList());
    }
}