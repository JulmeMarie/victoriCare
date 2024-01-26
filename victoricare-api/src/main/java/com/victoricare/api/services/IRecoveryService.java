package com.victoricare.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.victoricare.api.entities.Recovery;
import com.victoricare.api.entities.User;
import com.victoricare.api.security.JWTToken;

public interface IRecoveryService {
    Recovery doCreate(JWTToken jwtToken, String email);

    Recovery doResend(JWTToken jwtToken, String email, Integer id);

    Recovery doValidate(JWTToken jwtToken, Integer id, Integer code);

    void doDelete(User onlineUser, Integer id);

    void doCancel(Integer id);

    Recovery doGet(Integer id);

    Recovery findByEmail(String email);

    Page<Recovery> doPage(Pageable pageable);
}