package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.inputs.UserInputDTO;
import com.victoricare.api.entities.ChildCare;
import com.victoricare.api.entities.Family;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.enums.ERight;
import com.victoricare.api.enums.ERole;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.repositories.IUserRepository;
import com.victoricare.api.services.IUserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService extends PersonService implements IUserService {

    @Autowired
    private IUserRepository repository;

    @Override
    public User doCreate(User onlineUser, UserInputDTO dto) {
        User user = (User) super.initPerson(onlineUser, new User(), dto);
        user.setAccountCreationDate(new Date());
        user.setCreationDate(new Date());
        user.setCreationAuthorId(onlineUser.getCreationAuthorId());
        user.setDescription(dto.getDescription());
        user.setEmail(dto.getEmail());
        user.setPseudo(dto.getPseudo());
        user.setRights(ERight.USER.name());
        user.setRoles(ERole.USER.name());
        user.setUniqueIdentifier(super.codeService.doCreate(CodeService.ECodeLength.TEN).getId());
        this.repository.save(user);
        return user;
    }

    @Override
    public User doUpdate(User onlineUser, UserInputDTO dto, Integer id) {
        User user = this.doGet(onlineUser, id);
        user = (User) super.initPerson(onlineUser, user, dto);
        user.setUpdateAuthorId(onlineUser.getId());
        user.setUpdateDate(new Date());
        user.setDescription(dto.getDescription());
        this.repository.save(user);
        return user;
    }

    @Override
    public void doDelete(User onlineUser, Integer id) {
        User user = this.doGet(onlineUser, id);
        this.repository.delete(user);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + User.class + " with id : [" + id + "]");
    }

    @Override
    public User doGet(User onlineUser, Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.USER_NOT_FOUND));
    }

    @Override
    public Page<User> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<User> getAllAdmins() {
        return this.repository.findAllAdmins();
    }

    public static boolean isParent(Integer userId, Family family) {
        boolean isParent = userId == family.getCreationAuthorId();
        if (!isParent) {
            isParent = family.getParentOne() != null && userId == family.getParentOne().getId();
            if (!isParent) {
                isParent = family.getParentTwo() != null && userId == family.getParentTwo().getId();
            }
        }
        return isParent;
    }

    public static boolean isBabySitter(Integer userId, Family family) {
        return family.getBabySitter() != null && userId == family.getBabySitter().getUserId();
    }

    public static boolean isBabySitter(Integer userId, ChildCare childCare) {
        return childCare.getBabySitter() != null && userId == childCare.getBabySitter().getUserId();
    }

    @Override
    public User doGet(String email) {
        return this.repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.USER_NOT_FOUND));
    }

    @Override
    public List<User> doListByEmailOrPseudo(String email, String pseudo) {
        return this.repository.findByEmailOrPseudo(email, pseudo);
    }
}