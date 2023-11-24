package com.victoricare.api.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.victoricare.api.dtos.inputs.NotificationInputDTO;
import com.victoricare.api.entities.Notification;
import com.victoricare.api.entities.User;

public interface INotificationService {

    Notification doCreate(NotificationInputDTO dto);

    Notification doView(User onlineUser, Long id);

    Notification doRead(User onlineUser, Long id);

    void doDelete(User onlineUser, Long id);

    Notification doGet(User onlineUser, Long id);

    Page<Notification> doPage(Pageable pageable);

    List<Notification> doListByUser(User onlineUser, Integer userId);

    List<Notification> doListByBabySitter(User onlineUser, Long id);
}
