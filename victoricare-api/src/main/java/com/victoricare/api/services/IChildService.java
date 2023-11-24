package com.victoricare.api.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.dtos.inputs.ChildInputDTO;
import com.victoricare.api.entities.Child;
import com.victoricare.api.entities.User;

public interface IChildService {

    Child doCreate(User onlineUser, ChildInputDTO dto);

    Child doUpdate(User onlineUser, ChildInputDTO dto, Integer id);

    void doDelete(User onlineUser, Integer id);

    Child doGet(User onlineUser, Integer id);

    Page<Child> doPage(Pageable pageable);

    List<Child> doListByFamily(User onlineUser, Integer familyId);
}
