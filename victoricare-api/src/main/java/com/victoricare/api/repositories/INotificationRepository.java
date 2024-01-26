package com.victoricare.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.Notification;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n from Notification n WHERE n.destinationId = ?1 AND n.deletionDate = null AND n.type ='USER'")
    List<Notification> findAllByUser(Integer userId);

    @Query("SELECT n from Notification n WHERE n.destinationId = ?1 AND n.deletionDate = null AND n.type ='BABY_SITTER'")
    List<Notification> findAllByBabySitter(Long userId);

}
