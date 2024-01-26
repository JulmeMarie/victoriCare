package com.victoricare.api.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.dtos.inputs.FamilyInputDTO;
import com.victoricare.api.entities.Family;
import com.victoricare.api.entities.User;

public interface IFamilyService {

    Family doCreate(User onlineUser, FamilyInputDTO dto);

    Family doUpdate(User onlineUser, FamilyInputDTO dto, Integer id);

    void doDelete(User onlineUser, Integer id);

    Family doGet(User onlineUser, Integer id);

    Page<Family> doPage(Pageable pageable);

    List<Family> doListByParent(User onlineUser, Integer userId);
}
