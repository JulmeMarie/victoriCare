package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.ContactOutputDTO;
import com.victoricare.api.entities.Contact;

public class ContactMapper {

    public static ContactOutputDTO toOutput(Contact contact) {
        if (contact == null)
            return null;

        return ContactOutputDTO.builder()
                .id(contact.getId())
                .name(contact.getName())
                .email(contact.getEmail())
                .text(contact.getText())
                .subject(contact.getSubject())
                .response(contact.getResponse())
                .creationDate(contact.getCreationDate())
                .responseDate(contact.getResponseDate())
                .responseAuthor(UserMapper.toOutput(contact.getResponseAuthor()))
                .build();
    }
}