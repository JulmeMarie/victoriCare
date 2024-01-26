package com.victoricare.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.entities.Banishment;
import com.victoricare.api.security.JWTToken;

public interface IBanishmentService {

    void doCreate(JWTToken jwtToken);

    void doDelete(IDeletionHistoryService deletionHistoryService, Integer onlineUserId, Integer id);

    Banishment doGet(Integer id);

    Page<Banishment> doPage(Pageable pageable);

}
