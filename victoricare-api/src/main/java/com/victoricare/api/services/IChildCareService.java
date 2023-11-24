package com.victoricare.api.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.dtos.inputs.ChildCareInputDTO;
import com.victoricare.api.entities.ChildCare;
import com.victoricare.api.entities.User;

public interface IChildCareService {

    ChildCare doCreate(User onlineUser, ChildCareInputDTO dto);

    ChildCare doUpdate(User onlineUser, ChildCareInputDTO dto, Long careId);

    void doCancel(User onlineUser, Long id);

    void doDelete(User onlineUser, Long id);

    ChildCare doGet(User onlineUser, Long id);

    Page<ChildCare> doPage(Pageable pageable);

    List<ChildCare> doListByFamily(User onlineUser, Integer id);
}
