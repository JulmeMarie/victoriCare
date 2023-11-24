package com.victoricare.api.services.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.inputs.NotificationInputDTO;
import com.victoricare.api.entities.Notification;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.repositories.INotificationRepository;
import com.victoricare.api.services.INotificationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationService implements INotificationService {

    @Autowired
    private INotificationRepository repository;

    @Override
    public Notification doCreate(NotificationInputDTO dto) {
        Notification notification = new Notification();
        notification.setCreationDate(new Date());
        notification.setContent(dto.getContent());
        notification.setDestinationId(dto.getDestinationId());
        notification.setType(dto.getType());
        notification.setTitle(dto.getTitle());
        this.repository.save(notification);
        return notification;
    }

    @Override
    public Notification doView(User onlineUser, Long id) {
        Notification notification = this.doGet(onlineUser, id);
        notification.setViewDate(new Date());
        this.repository.save(notification);
        return notification;
    }

    @Override
    public Notification doRead(User onlineUser, Long id) {
        Notification notification = this.doGet(onlineUser, id);
        notification.setReadingDate(new Date());
        this.repository.save(notification);
        return notification;
    }

    @Override
    public void doDelete(User onlineUser, Long id) {
        Notification notification = this.doGet(onlineUser, id);
        this.repository.delete(notification);

        log.info("User : [" + onlineUser.getUniqueIdentifier() + "] has deleted "
                + Notification.class + " with id : [" + id + "]");
    }

    @Override
    public Notification doGet(User onlineUser, Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMessage.NOTIFICATION_NOT_FOUND));
    }

    @Override
    public Page<Notification> doPage(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<Notification> doListByUser(User onlineUser, Integer userId) {
        return this.repository.findAllByUser(userId);
    }

    @Override
    public List<Notification> doListByBabySitter(User onlineUser, Long babySitterId) {
        return this.repository.findAllByBabySitter(babySitterId);
    }

}
