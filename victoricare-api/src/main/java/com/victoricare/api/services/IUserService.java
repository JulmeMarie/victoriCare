package com.victoricare.api.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.dtos.inputs.UserInputDTO;
import com.victoricare.api.entities.User;

public interface IUserService {

    User doCreate(User onlineUser, UserInputDTO dto);

    User doUpdate(User onlineUser, UserInputDTO dto, Integer id);

    void doDelete(User onlineUser, Integer id);

    User doGet(User onlineUser, Integer id);

    User doGet(String email);

    Page<User> doPage(Pageable pageable);

    List<User> getAllAdmins();

    List<User> doListByEmailOrPseudo(String email, String pseudo);
}
