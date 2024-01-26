package com.victoricare.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.entities.Banishment;
import com.victoricare.api.entities.User;
import com.victoricare.api.security.JWTToken;

public interface IBanishmentService {

    void doCreate(JWTToken jwtToken);

    public void doCancel(User user, Integer id);

    void doDelete(User onlineUser, Integer id);

    Banishment doGet(Integer id);

    Page<Banishment> doPage(Pageable pageable);

}
