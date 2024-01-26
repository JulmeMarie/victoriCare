package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.inputs.ChildInputDTO;
import com.victoricare.api.entities.Child;
import com.victoricare.api.entities.Family;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.repositories.IChildRepository;
import com.victoricare.api.services.IChildService;
import com.victoricare.api.services.IFamilyService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChildService extends PersonService implements IChildService {

    @Autowired
    private IChildRepository repository;

    @Autowired
    private IFamilyService familyService;

    @Override
    public Child doCreate(User onlineUser, ChildInputDTO dto) {
        Integer userId = onlineUser.getId();
        Family family = this.familyService.doGet(onlineUser, dto.getFamilyId());

        if (!UserService.isParent(userId, family)) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_PARENT);
        }

        Child child = (Child) super.initPerson(onlineUser, new Child(), dto);
        child.setCreationAuthorId(userId);
        child.setFamily(family);
        child.setCreationDate(new Date());
        child.setUniqueIdentifier(this.codeService.doCreate(CodeService.ECodeLength.TEN).getId());
        this.repository.save(child);
        return child;
    }

    @Override
    public Child doUpdate(User onlineUser, ChildInputDTO dto, Integer id) {
        Integer userId = onlineUser.getId();

        Child child = this.doGet(onlineUser, id);

        if (!UserService.isParent(userId, child.getFamily())) {
            Family attachment = child.getAttachmentFamily();
            if (attachment == null || !UserService.isParent(userId, attachment)) {
                throw new ActionNotAllowedException(EMessage.USER_NOT_PARENT);
            }
        }

        child = (Child) super.initPerson(onlineUser, child, dto);
        child.setUpdateDate(new Date());
        child.setUpdateAuthorId(onlineUser.getId());
        this.repository.save(child);
        return child;
    }

    @Override
    public void doDelete(User onlineUser, Integer id) {
        Child child = this.doGet(onlineUser, id);
        this.repository.delete(child);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + Child.class + " with id : [" + id + "]");
    }

    @Override
    public Child doGet(User onlineUser, Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.CHILD_NOT_FOUND));
    }

    @Override
    public Page<Child> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<Child> doListByFamily(User onlineUser, Integer familyId) {
        return this.repository.findAllByFamily(familyId);
    }
}