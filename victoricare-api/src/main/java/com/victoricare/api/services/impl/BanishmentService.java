package com.victoricare.api.services.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.entities.Banishment;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.repositories.IBanishmentRepository;
import com.victoricare.api.security.JWTToken;
import com.victoricare.api.services.IBanishmentService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BanishmentService implements IBanishmentService {

    @Autowired
    private IBanishmentRepository repository;

    @Override
    public void doCreate(JWTToken jwtToken) {
        Banishment banishment = new Banishment();
        banishment.setBrowser(jwtToken.getBrowser());
        banishment.setIp(jwtToken.getIp());
        banishment.setCreationDate(new Date());
        this.repository.save(banishment);
    }

    @Override
    public void doCancel(User user, Integer id) {
        Banishment exisitingBanishment = this.doGet(id);

        if (exisitingBanishment.getDeletionDate() != null) {
            throw new ActionNotAllowedException(EMessage.ADDRESS_HAS_BEEN_CANCELED);
        }

        exisitingBanishment.setDeletionDate(new Date());
        exisitingBanishment.setDeletionAuthorId(user.getId());
        this.repository.save(exisitingBanishment);
    }

    @Override
    public void doDelete(User onlineUser, Integer id) {
        Banishment existingBanishment = this.doGet(id);
        this.repository.delete(existingBanishment);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + Banishment.class + " with id : [" + id + "]");
    }

    @Override
    public Banishment doGet(Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.BANISHMENT_NOT_FOUND));
    }

    @Override
    public Page<Banishment> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}
