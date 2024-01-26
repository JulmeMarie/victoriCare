package com.victoricare.api.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = true)
    private Long id;

    @Column(name = "notification_content", nullable = false, length = 500)
    private String content;

    @Column(name = "notification_destination", nullable = false)
    private Long destinationId;

    @Column(name = "notification_title", nullable = true, length = 50)
    private String title;

    @Column(name = "notification_type", nullable = false, length = 10)
    private String type;

    @Column(name = "notification_creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "notification_reading_date", nullable = true)
    private Date readingDate;

    @Column(name = "notification_view_date", nullable = true)
    private Date viewDate;

    @Column(name = "notification_deletion_date", nullable = true)
    private Date deletionDate;
}
