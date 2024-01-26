package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.inputs.TaskInputDTO;
import com.victoricare.api.entities.Task;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.repositories.ITaskRepository;
import com.victoricare.api.services.ITaskService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskService implements ITaskService {

    @Autowired
    private ITaskRepository repository;

    @Override
    public Task doCreate(User onlineUser, TaskInputDTO dto) {
        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setCreationAuthorId(onlineUser.getId());
        task.setCreationDate(new Date());
        repository.save(task);
        return task;
    }

    @Override
    public Task doUpdate(User onlineUser, TaskInputDTO dto, Long id) {
        Task task = this.doGet(onlineUser, id);
        if (task.getDeletionDate() != null) {
            throw new ActionNotAllowedException(EMessage.TASK_HAS_BEEN_CANCELED);
        }
        task.setUpdateDate(new Date());
        task.setDescription(dto.getDescription());
        task.setName(dto.getName());
        task.setUpdateAuthorId(onlineUser.getId());
        this.repository.save(task);
        return task;
    }

    @Override
    public void doCancel(User user, Long id) {
        Task exisitingTask = this.doGet(user, id);

        if (exisitingTask.getDeletionDate() != null) {
            throw new ActionNotAllowedException(EMessage.TASK_HAS_BEEN_CANCELED);
        }

        exisitingTask.setDeletionDate(new Date());
        exisitingTask.setDeletionAuthorId(user.getId());
        this.repository.save(exisitingTask);
    }

    @Override
    public void doDelete(User onlineUser, Long id) {
        Task task = this.doGet(onlineUser, id);
        this.repository.delete(task);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + Task.class + " with id : [" + id + "]");
    }

    @Override
    public Task doGet(User onlineUser, Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.TASK_NOT_FOUND));
    }

    @Override
    public Page<Task> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<Task> doListByUser(User onlineUser, Integer userId) {
        return this.repository.findAllByUser(userId);
    }

}
