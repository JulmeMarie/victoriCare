package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.inputs.ChildCareTaskInputDTO;
import com.victoricare.api.entities.ChildCareTask;
import com.victoricare.api.entities.Family;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.entities.ChildCare;
import com.victoricare.api.repositories.IChildCareTaskRepository;
import com.victoricare.api.services.IChildCareService;
import com.victoricare.api.services.IChildCareTaskService;
import com.victoricare.api.services.IFamilyService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChildCareTaskService implements IChildCareTaskService {

    @Autowired
    private IChildCareTaskRepository repository;

    @Autowired
    private IChildCareService childCareService;

    @Autowired
    private IFamilyService familyService;

    private boolean isAResponsible(Integer userId, Family family, ChildCare childCare) {
        return UserService.isParent(userId, family) ||
                UserService.isBabySitter(userId, family) ||
                UserService.isBabySitter(userId, childCare);
    }

    @Override
    public ChildCareTask doCreate(User onlineUser, ChildCareTaskInputDTO dto) {
        Integer userId = onlineUser.getId();

        ChildCare childCare = this.childCareService.doGet(onlineUser, dto.getChildCareId());
        Family family = this.familyService.doGet(onlineUser, childCare.getCreationFamilyId());

        if (!this.isAResponsible(userId, family, childCare)) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_PARENT);
        }

        ChildCareTask childCareTask = ChildCareTask.builder()
                .creationAuthorId(userId)
                .childCare(childCare)
                .taskName(dto.getTaskName())
                .taskDescription(dto.getTaskDescription())
                .childId(dto.getChildId())
                .creationDate(new Date())
                .creationAuthorId(userId)
                .build();
        this.repository.save(childCareTask);
        return childCareTask;
    }

    @Override
    public ChildCareTask doUpdate(User onlineUser, ChildCareTaskInputDTO dto, Long childCareTaskId) {
        Integer userId = onlineUser.getId();

        ChildCareTask childCareTask = this.doGet(onlineUser, childCareTaskId);
        ChildCare childCare = this.childCareService.doGet(onlineUser, childCareTask.getChildCare().getId());

        Family family = this.familyService.doGet(onlineUser, childCare.getCreationFamilyId());

        if (!this.isAResponsible(userId, family, childCare)) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_PARENT);
        }

        childCareTask.setTaskName(dto.getTaskName());
        childCareTask.setTaskDescription(dto.getTaskDescription());
        childCareTask.setAupdateAuthorId(userId);
        childCareTask.setAupdateDate(new Date());

        this.repository.save(childCareTask);
        return childCareTask;
    }

    @Override
    public void doCancel(User onlineUser, Long id) {
        Integer userId = onlineUser.getId();
        ChildCareTask exisitingChildCareTask = this.doGet(onlineUser, id);

        Family family = this.familyService.doGet(onlineUser,
                exisitingChildCareTask.getChildCare().getCreationFamilyId());

        if (!this.isAResponsible(userId, family, exisitingChildCareTask.getChildCare())) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_PARENT);
        }

        exisitingChildCareTask.setDeletionDate(new Date());
        exisitingChildCareTask.setDeletionAuthorId(userId);
        this.repository.save(exisitingChildCareTask);
    }

    @Override
    public void doDelete(User onlineUser, Long id) {
        ChildCareTask chikdCareTask = this.doGet(onlineUser, id);
        this.repository.delete(chikdCareTask);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + ChildCareTask.class + " with id : [" + id + "]");
    }

    @Override
    public ChildCareTask doGet(User onlineUser, Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.CHILD_CARE_TASK_NOT_FOUND));
    }

    @Override
    public Page<ChildCareTask> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<ChildCareTask> doListByChildCare(User onlineUser, Long childCareId) {
        return this.repository.findAllByChildCare(childCareId);
    }
}