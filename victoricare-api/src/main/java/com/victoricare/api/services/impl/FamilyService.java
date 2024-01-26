package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.inputs.FamilyInputDTO;
import com.victoricare.api.entities.Family;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.repositories.IFamilyRepository;
import com.victoricare.api.services.IFamilyService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FamilyService implements IFamilyService {

    @Autowired
    private IFamilyRepository repository;

    @Override
    public Family doCreate(User onlineUser, FamilyInputDTO dto) {
        Family family = new Family();
        family.setCreationAuthorId(onlineUser.getId());
        family.setCreationDate(new Date());
        family.setParentOne(onlineUser);
        this.repository.save(family);
        return family;
    }

    @Override
    public Family doUpdate(User onlineUser, FamilyInputDTO dto, Integer id) {
        Family existingFamily = this.doGet(onlineUser, id);

        if (existingFamily.getDeletionDate() != null) {
            throw new ActionNotAllowedException(EMessage.FAMILY_ALREADY_CANCELLED);
        }

        if (!UserService.isParent(onlineUser.getId(), existingFamily)) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_PARENT);
        }

        existingFamily.setUpdateAuthorId(onlineUser.getId());
        existingFamily.setUpdateDate(new Date());
        this.repository.save(existingFamily);
        return existingFamily;
    }

    @Override
    public void doDelete(User onlineUser, Integer id) {
        Family existingFamily = this.doGet(onlineUser, id);
        this.repository.delete(existingFamily);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + Family.class + " with id : [" + id + "]");
    }

    @Override
    public Family doGet(User onlineUser, Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.FAMILY_NOT_FOUND));
    }

    @Override
    public Page<Family> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<Family> doListByParent(User onlineUser, Integer userId) {
        return this.repository.findAllByParent(userId);
    }
}
