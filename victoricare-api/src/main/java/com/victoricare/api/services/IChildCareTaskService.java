package com.victoricare.api.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.dtos.inputs.ChildCareTaskInputDTO;
import com.victoricare.api.entities.ChildCareTask;
import com.victoricare.api.entities.User;

public interface IChildCareTaskService {

    ChildCareTask doCreate(User onlineUser, ChildCareTaskInputDTO dto);

    ChildCareTask doUpdate(User onlineUser, ChildCareTaskInputDTO dto, Long careId);

    void doCancel(User onlineUser, Long id);

    void doDelete(User onlineUser, Long id);

    ChildCareTask doGet(User onlineUser, Long id);

    Page<ChildCareTask> doPage(Pageable pageable);

    List<ChildCareTask> doListByChildCare(User onlineUser, Long childCareId);
}
