package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.inputs.BabySitterInputDTO;
import com.victoricare.api.entities.BabySitter;
import com.victoricare.api.entities.Family;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.repositories.IBabySitterRepository;
import com.victoricare.api.services.IBabySitterService;
import com.victoricare.api.services.ICodeService;
import com.victoricare.api.services.IFamilyService;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BabySitterService implements IBabySitterService {

    @Autowired
    private IBabySitterRepository repository;

    @Autowired
    private ICodeService codeService;

    @Autowired
    IFamilyService familyService;

    @Override
    public BabySitter doCreate(User user, BabySitterInputDTO dto) {

        Family exisitingFamily = this.familyService.doGet(user, dto.getFamilyId());

        if (!UserService.isParent(user.getId(), exisitingFamily)) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_PARENT);
        }

        BabySitter babySitter = BabySitter.builder()
                .creationDate(new Date())
                .creationAuthorId(user.getId())
                .family(exisitingFamily)
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .code(this.codeService.doCreate(CodeService.ECodeLength.SIX).getId())
                .build();

        this.repository.save(babySitter);
        return babySitter;
    }

    @Override
    public BabySitter doUpdate(User user, BabySitterInputDTO dto, Long babySitterId) {

        BabySitter existingBabySitter = this.doGet(user.getId(), babySitterId);

        if (existingBabySitter.getDeletionDate() != null) {
            throw new ActionNotAllowedException(EMessage.BABY_SITTER_ALREADY_CANCELLED);
        }

        if (!UserService.isParent(user.getId(), existingBabySitter.getFamily())) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_PARENT);
        }

        existingBabySitter.setUpdateAuthorId(user.getId());
        existingBabySitter.setLastname(dto.getLastname());
        existingBabySitter.setFirstname(dto.getFirstname());
        existingBabySitter.setUpdateDate(new Date());
        this.repository.save(existingBabySitter);
        return existingBabySitter;
    }

    @Override
    public BabySitter doGet(Integer onlineUserId, Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.BABY_SITTER_NOT_FOUND));
    }

    @Override
    public void doDelete(User onlineUser, Long id) {
        BabySitter existingBabySitter = this.doGet(onlineUser.getId(), id);
        this.repository.delete(existingBabySitter);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + BabySitter.class + " with id : [" + id + "]");
    }

    @Override
    public void doCancel(User user, Long id) {
        BabySitter existingBabySitter = this.doGet(user.getId(), id);
        if (!user.getId().equals(existingBabySitter.getCreationAuthorId())) {
            throw new ActionNotAllowedException(EMessage.USER_NOT_AUTHOR);
        }
        existingBabySitter.setDeletionDate(new Date());
        existingBabySitter.setDeletionAuthorId(user.getId());
        this.repository.save(existingBabySitter);
    }

    @Override
    public Page<BabySitter> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<BabySitter> doListByFamily(User user, Integer familyId) {
        return this.repository.findAllByFamilyId(familyId);
    }

    @Override
    public List<BabySitter> doListByUser(User user, Integer userId) {
        return this.repository.findByUserId(userId);
    }
}