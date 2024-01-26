package com.victoricare.api.services.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.victoricare.api.entities.Recovery;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.InvalidInputException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.mailers.IRecoveryMailer;
import com.victoricare.api.repositories.IRecoveryRepository;
import com.victoricare.api.security.JWTToken;
import com.victoricare.api.services.IRecoveryService;
import com.victoricare.api.services.IUserService;
import com.victoricare.api.utils.CustomRandom;
import com.victoricare.api.enums.EMessage;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Slf4j
@Service
public class RecoveryService implements IRecoveryService {
    @Value("${victoricare.api.recovery.timeout.minute}")
    private Integer timeout;

    @Autowired
    private IRecoveryMailer recoveryMailer;

    @Autowired
    private IRecoveryRepository repository;

    @Autowired
    private IUserService userService;

    @Override
    public Recovery doCreate(JWTToken jwtToken, String email) {
        User user = this.userService.doGet(email);
        if (user.getAccountDeletionDate() != null || user.getDeletionDate() != null) {
            throw new ResourceNotFoundException(EMessage.USER_NOT_FOUND);
        }
        Recovery recovery = Recovery.builder()
                .creationAuthor(user)
                .email(email)
                .creationDate(new Date())
                .build();

        return this.updateOrSave(recovery, jwtToken.getIp(), jwtToken.getBrowser());
    }

    @Override
    public Recovery doResend(JWTToken jwtToken, String email, Integer id) {
        Recovery recovery = this.doGet(id);

        if (recovery.getDeletionDate() != null) {
            throw new ActionNotAllowedException(EMessage.RECOVERY_ALREADY_DELETED);
        }
        if (recovery.getValidatingDate() != null) {
            throw new ActionNotAllowedException(EMessage.CODE_ALREADY_USED);
        }
        if (!recovery.getEmail().equals(email)) {
            throw new ActionNotAllowedException(EMessage.RECOVERY_NOT_FOUND);
        }
        return this.updateOrSave(recovery, jwtToken.getIp(), jwtToken.getBrowser());
    }

    @Transactional
    private Recovery updateOrSave(Recovery recovery, String ip, String browser) {
        int TIMEOUT_IN_MILIS = timeout * 60 * 1000;
        long expiration = new Date().getTime() + TIMEOUT_IN_MILIS;

        Integer code = CustomRandom.get6DigitCode();
        recovery.setCode(String.valueOf(code));
        recovery.setCodeDate(new Date());
        recovery.setCodeExpirationDate(new Date(expiration));
        recovery.setIp(ip);
        recovery.setBrowser(browser);
        this.repository.save(recovery);
        this.recoveryMailer.sendCode(recovery);
        return recovery;
    }

    @Override
    public Recovery doValidate(JWTToken jwtToken, Integer id, Integer code) {
        Recovery existingRecovery = this.doGet(id);

        if (!existingRecovery.getCode().equals(String.valueOf(code))) {
            throw new InvalidInputException(EMessage.CODE_NOT_MATCH);
        }

        Date now = new Date();
        if (existingRecovery.getCodeExpirationDate().before(now)) {
            this.updateOrSave(existingRecovery, jwtToken.getIp(), jwtToken.getBrowser());
            throw new InvalidInputException(EMessage.CODE_EXPIRED);
        }

        existingRecovery.setValidatingDate(now);
        this.repository.save(existingRecovery);
        this.recoveryMailer.sendConfirmation(existingRecovery);
        return existingRecovery;
    }

    @Override
    public void doCancel(Integer id) {
        Recovery recovery = this.doGet(id);

        if (recovery.getDeletionDate() != null) {
            throw new ActionNotAllowedException(EMessage.RECOVERY_ALREADY_DELETED);
        }
        recovery.setDeletionDate(new Date());
        this.repository.save(recovery);
    }

    @Override
    public void doDelete(User onlineUser, Integer id) {
        Recovery recovery = this.doGet(id);
        this.repository.delete(recovery);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + Recovery.class + " with id : [" + id + "]");
    }

    @Override
    public Recovery doGet(Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.RECOVERY_NOT_FOUND));
    }

    @Override
    public Page<Recovery> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public Recovery findByEmail(String email) {
        return this.repository.findByEmail(email);
    }
}
