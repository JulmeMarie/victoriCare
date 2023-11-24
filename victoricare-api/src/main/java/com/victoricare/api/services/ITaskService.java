package com.victoricare.api.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.dtos.inputs.TaskInputDTO;
import com.victoricare.api.entities.Task;
import com.victoricare.api.entities.User;

public interface ITaskService {

    Task doCreate(User onlineUser, TaskInputDTO dto);

    Task doUpdate(User onlineUser, TaskInputDTO dto, Long id);

    void doCancel(User user, Long id);

    void doDelete(User onlineUser, Long id);

    Task doGet(User onlineUser, Long id);

    Page<Task> doPage(Pageable pageable);

    List<Task> doListByUser(User onlineUser, Integer userId);
}
