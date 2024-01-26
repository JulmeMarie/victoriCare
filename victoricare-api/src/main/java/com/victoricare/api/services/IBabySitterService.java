package com.victoricare.api.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.dtos.inputs.BabySitterInputDTO;
import com.victoricare.api.entities.BabySitter;
import com.victoricare.api.entities.User;

public interface IBabySitterService {

    BabySitter doCreate(User user, BabySitterInputDTO dto);

    BabySitter doUpdate(User user, BabySitterInputDTO dto, Long accessId);

    BabySitter doGet(Integer onlineUserId, Long id);

    void doDelete(User onlineUser, Long id);

    void doCancel(User user, Long id);

    Page<BabySitter> doPage(Pageable pageable);

    List<BabySitter> doListByUser(User user, Integer userId);

    List<BabySitter> doListByFamily(User user, Integer familyId);
}
