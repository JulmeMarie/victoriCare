package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.victoricare.api.dtos.inputs.ChildCareInputDTO;
import com.victoricare.api.entities.ChildCare;
import com.victoricare.api.entities.Family;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.repositories.IChildCareRepository;
import com.victoricare.api.services.IChildCareService;
import com.victoricare.api.services.IFamilyService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChildCareService implements IChildCareService {

    @Autowired
    private IChildCareRepository repository;

    @Autowired
    private IFamilyService familyService;

    @Transactional
    @Override
    public ChildCare doCreate(User onlineUser, ChildCareInputDTO dto) {
        Integer userId = onlineUser.getId();
        Family family = this.familyService.doGet(onlineUser, dto.getCreationFamilyId());

        if (!UserService.isParent(userId, family)) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_PARENT);
        }

        ChildCare childCare = new ChildCare();
        childCare.setCreationAuthorId(userId);
        childCare.setCreationDate(new Date());
        childCare.setCreationFamilyId(family.getId());
        childCare.setStartDate(dto.getStartDate());
        childCare.setEndDate(dto.getEndDate());
        this.repository.save(childCare);
        return childCare;
    }

    @Override
    public ChildCare doUpdate(User onlineUser, ChildCareInputDTO dto, Long careId) {
        Integer userId = onlineUser.getId();

        Family family = this.familyService.doGet(onlineUser, dto.getCreationFamilyId());
        if (!UserService.isParent(userId, family)) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_PARENT);
        }

        ChildCare existingChildCare = this.doGet(onlineUser, careId);
        if (family.getId() != existingChildCare.getCreationFamilyId()) {
            throw new ActionNotAllowedException(EMessage.CHILD_CARE_NOT_FOUND);
        }
        existingChildCare.setUpdateDate(new Date());
        existingChildCare.setUpdateAuthorId(userId);
        existingChildCare.setStartDate(dto.getStartDate());
        existingChildCare.setEndDate(dto.getEndDate());
        this.repository.save(existingChildCare);
        return existingChildCare;
    }

    @Override
    public void doCancel(User onlineUser, Long id) {
        Integer userId = onlineUser.getId();
        ChildCare exisitingChildCare = this.doGet(onlineUser, id);

        Family family = this.familyService.doGet(onlineUser, exisitingChildCare.getCreationFamilyId());
        if (!UserService.isParent(userId, family)) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_PARENT);
        }
        exisitingChildCare.setDeletionDate(new Date());
        exisitingChildCare.setDeletionAuthorId(userId);
        this.repository.save(exisitingChildCare);
    }

    @Override
    public void doDelete(User onlineUser, Long id) {
        ChildCare existingChildCare = this.doGet(onlineUser, id);
        this.repository.delete(existingChildCare);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + ChildCare.class + " with id : [" + id + "]");
    }

    @Override
    public ChildCare doGet(User onlineUser, Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.CHILD_CARE_NOT_FOUND));
    }

    @Override
    public Page<ChildCare> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<ChildCare> doListByFamily(User onlineUser, Integer id) {
        return this.repository.findAllByFamily(id);
    }
}