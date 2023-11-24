package com.victoricare.api.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.dtos.inputs.ContactInputDTO;
import com.victoricare.api.entities.Contact;
import com.victoricare.api.entities.User;
import com.victoricare.api.security.JWTToken;

public interface IContactService {

    Contact doCreate(JWTToken jwtToken, ContactInputDTO dto);

    Contact doResponse(User onlineUser, String message, Integer id);

    void doDelete(User onlineUser, Integer id);

    Contact doGet(User onlineUser, Integer id);

    Page<Contact> doPage(Pageable pageable);

    List<Contact> doListByUser(User onlineUser, Integer userId);
}
