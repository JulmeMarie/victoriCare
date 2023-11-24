package com.victoricare.api.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.victoricare.api.dtos.inputs.SignUpInputDTO;
import com.victoricare.api.entities.SignUp;
import com.victoricare.api.entities.User;
import com.victoricare.api.security.JWTToken;

public interface ISignUpService {

    SignUp doCreate(JWTToken jwtToken, PasswordEncoder encoder, SignUpInputDTO dto);

    SignUp doValidate(JWTToken jwtToken, Integer id, Integer code);

    void doCancel(Integer id);

    void doDelete(User onlineUser, Integer id);

    Page<SignUp> doPage(Pageable pageable);

    SignUp doGet(Integer id);

    List<SignUp> doListByEmailOrPseudo(String email, String pseudo);
}
