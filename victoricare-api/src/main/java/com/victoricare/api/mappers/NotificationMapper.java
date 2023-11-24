package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.NotificationOutputDTO;
import com.victoricare.api.entities.Notification;
import jakarta.annotation.Nullable;

public class NotificationMapper {

    public static NotificationOutputDTO toOutput(@Nullable Notification notification) {
        if (notification == null)
            return null;

        return NotificationOutputDTO.builder()
                .id(notification.getId())
                .content(notification.getContent())
                .destinationId(notification.getDestinationId())
                .type(notification.getType())
                .creationDate(notification.getCreationDate())
                .readingDate(notification.getReadingDate())
                .viewDate(notification.getViewDate())
                .deletionDate(notification.getDeletionDate())
                .build();

    }

}
