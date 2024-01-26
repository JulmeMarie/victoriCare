package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.victoricare.api.configurations.GuestConfig;
import com.victoricare.api.dtos.inputs.LogInInputDTO;
import com.victoricare.api.entities.LogIn;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.repositories.ILogInRepository;
import com.victoricare.api.security.JWTToken;
import com.victoricare.api.security.services.UserDetailsImpl;
import com.victoricare.api.services.ILogInService;
import com.victoricare.api.enums.EMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LogInService implements ILogInService {

    @Autowired
    private GuestConfig guestConfig;

    @Autowired
    private ILogInRepository repository;

    @Override
    public LogIn doCreate(AuthenticationManager authenticationManager, JWTToken jwtToken, LogInInputDTO dto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getIdentifier(), dto.getPass()));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userDetails.getUser();
        Map<String, Object> token = jwtToken.generate(user.getPseudo());

        LogIn logIn = new LogIn();
        logIn.setCreationDate(new Date());
        logIn.setEmail(user.getEmail());
        logIn.setPseudo(user.getPseudo());
        logIn.setRights(user.getRights());
        logIn.setRoles(user.getRoles());
        logIn.setIp(jwtToken.getIp());
        logIn.setBrowser(jwtToken.getBrowser());
        logIn.setCreationAuthor(user);
        logIn.setExpirationDate(jwtToken.getExpirationDate(token));
        logIn.setToken(jwtToken.getJWT(token));

        this.repository.save(logIn);
        return logIn;
    }

    @Override
    public void doLogOut(User user, Long id) {
        LogIn logIn = this.doGet(id);
        logIn.setDeletionDate(new Date());
        this.repository.save(logIn);
    }

    @Override
    public void doDelete(User onlineUser, Long id) {
        LogIn logIn = this.doGet(id);
        this.repository.delete(logIn);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + LogIn.class + " with id : [" + id + "]");
    }

    @Override
    public String doCreateToken(JWTToken jwtToken) {

        String pseudo = this.guestConfig.getUser().getPseudo();
        Map<String, Object> token = jwtToken.generate(pseudo);
        return jwtToken.getJWT(token);
    }

    @Override
    public String doRefreshToken(User user, JWTToken jwtToken) {

        User guest = this.guestConfig.getUser();
        Map<String, Object> token = jwtToken.generate(user.getPseudo());

        if (!guest.equals(user)) {
            String oldJWT = jwtToken.getHttpHeader().getJwt();

            LogIn logIn = this.doGetByUserAndToken(user.getPseudo(), oldJWT);
            if (logIn == null) {
                throw new ResourceNotFoundException(EMessage.LOG_IN_NOT_FOUND);
            }

            logIn.setExpirationDate(jwtToken.getExpirationDate(token));
            logIn.setToken(jwtToken.getJWT(token));
            this.repository.save(logIn);
        }
        return jwtToken.getJWT(token);
    }

    @Override
    public LogIn doGet(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.LOG_IN_NOT_FOUND));
    }

    @Override
    public Page<LogIn> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public LogIn doGetByUserAndToken(String pseudo, String jwt) {
        List<LogIn> logIns = this.repository.findByPseudoAndToken(pseudo, jwt);
        return logIns.isEmpty() ? null : logIns.get(0);
    }
}
