package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.victoricare.api.dtos.inputs.SignUpInputDTO;
import com.victoricare.api.entities.SignUp;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.mailers.ISignUpMailer;
import com.victoricare.api.exceptions.InvalidInputException;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.repositories.ISignUpRepository;
import com.victoricare.api.security.JWTToken;
import com.victoricare.api.services.ISignUpService;
import com.victoricare.api.services.IUserService;
import com.victoricare.api.utils.CustomRandom;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Service
public class SignUpService implements ISignUpService {

    @Value("${victoricare.api.signup.timeout.minute}")
    private Integer timeout;

    @Autowired
    private ISignUpMailer signUpMailer;

    @Autowired
    private ISignUpRepository signUpRepository;

    @Autowired
    private IUserService userService;

    @Override
    public SignUp doCreate(JWTToken jwtToken, PasswordEncoder encoder, SignUpInputDTO dto) {

        String email = dto.getEmail();
        String pseudo = dto.getPseudo();

        // Step 1 : Let's check for existing users
        List<User> exisitingUsers = this.userService.doListByEmailOrPseudo(email, pseudo);
        if (!exisitingUsers.isEmpty()) {
            log.warn("User already exists, pseudo = {}", pseudo);
            if (exisitingUsers.get(0).getEmail().equals(email)) {
                throw new InvalidInputException(EMessage.USER_ALREADY_EXISTS);
            }
            throw new InvalidInputException(EMessage.PSEUDO_ALREADY_EXISTS);
        }

        SignUp signUp = null;

        // Step 2 : Let us check for existing sign-ups
        List<SignUp> exsitiSignUps = this.doListByEmailOrPseudo(email, pseudo);
        if (!exsitiSignUps.isEmpty()) {
            signUp = exsitiSignUps.get(0);
            log.warn("SignUp already exists, pseudo = {}", pseudo);
            if (!signUp.getEmail().equals(email)) {
                throw new InvalidInputException(EMessage.PSEUDO_ALREADY_EXISTS);
            }
        }
        if (signUp == null) {
            signUp = SignUp.builder().creationDate(new Date()).build();
        }

        signUp.setEmail(dto.getEmail());
        signUp.setPseudo(dto.getPseudo());
        signUp.setPass(encoder.encode(dto.getPass()));

        return this.updateOrSave(signUp, jwtToken.getIp(), jwtToken.getBrowser());
    }

    private Date getExpirationDate() {
        int timeout_in_milis = timeout * 60 * 1000;
        long expiration = new Date().getTime() + timeout_in_milis;
        return new Date(expiration);
    }

    @Override
    public SignUp doValidate(JWTToken jwtToken, Integer id, Integer code) {
        SignUp existingSignUp = this.doGet(id);

        if (!existingSignUp.getCode().equals(String.valueOf(code))) {
            throw new InvalidInputException(EMessage.CODE_NOT_MATCH);
        }

        Date now = new Date();

        if (existingSignUp.getCodeExpirationDate().before(now)) {
            this.updateOrSave(existingSignUp, jwtToken.getIp(), jwtToken.getBrowser());
            throw new InvalidInputException(EMessage.CODE_EXPIRED);
        }

        existingSignUp.setValidatingDate(now);
        this.signUpRepository.save(existingSignUp);
        this.signUpMailer.sendConfirmation(existingSignUp);
        return existingSignUp;
    }

    @Override
    public void doCancel(Integer id) {
        SignUp existingSignUp = this.doGet(id);

        if (existingSignUp.getValidatingDate() != null) {
            throw new ActionNotAllowedException(EMessage.SIGN_UP_ALREADY_VALIDATED);
        }
        existingSignUp.setCancelationDate(new Date());
        this.signUpRepository.save(existingSignUp);
    }

    @Transactional
    @Override
    public void doDelete(User onlineUser, Integer id) {
        SignUp existingSignUp = this.doGet(id);
        this.signUpRepository.delete(existingSignUp);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + SignUp.class + " with id : [" + id + "]");
    }

    @Transactional
    private SignUp updateOrSave(SignUp signUp, String ip, String browser) {
        Integer code = CustomRandom.get6DigitCode();
        signUp.setCode(String.valueOf(code));
        signUp.setCodeDate(new Date());
        signUp.setIp(ip);
        signUp.setBrowser(browser);
        signUp.setCodeExpirationDate(getExpirationDate());
        this.signUpRepository.save(signUp);
        this.signUpMailer.sendCode(signUp);
        return signUp;
    }

    @Override
    public List<SignUp> doListByEmailOrPseudo(String email, String pseudo) {
        return this.signUpRepository.findByEmailOrPseudo(email, pseudo);
    }

    @Override
    public SignUp doGet(Integer id) {
        return this.signUpRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.SIGN_UP_NOT_FOUND));
    }

    @Override
    public Page<SignUp> doPage(Pageable pageable) {
        return this.signUpRepository.findAll(pageable);
    }
}