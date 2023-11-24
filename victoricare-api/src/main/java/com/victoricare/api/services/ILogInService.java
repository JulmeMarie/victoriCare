package com.victoricare.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import com.victoricare.api.dtos.inputs.LogInInputDTO;
import com.victoricare.api.entities.LogIn;
import com.victoricare.api.entities.User;
import com.victoricare.api.security.JWTToken;

public interface ILogInService {

    LogIn doCreate(AuthenticationManager authenticationManager, JWTToken jwtToken, LogInInputDTO dto);

    void doLogOut(User user, Long id);

    void doDelete(User onlineUser, Long id);

    LogIn doGet(Long id);

    LogIn doGetByUserAndToken(String pseudo, String jwt);

    Page<LogIn> doPage(Pageable pageable);

    String doCreateToken(JWTToken jwtToken);

    String doRefreshToken(User user, JWTToken jwtToken);
}
