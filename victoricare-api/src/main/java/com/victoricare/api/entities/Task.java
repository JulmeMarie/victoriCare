package com.victoricare.api.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@NoArgsConstructor // JPA requires a no-arg constructor
@AllArgsConstructor // JPA requires an all-args constructor
@Builder
@Entity
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = true)
    private Long id;

    @Column(name = "task_name", nullable = false, length = 255)
    private String name;

    @Column(name = "task_description", nullable = true, length = 500)
    private String description;

    @Column(name = "task_creation_date", nullable = true)
    private Date creationDate;

    @Column(name = "task_update_date", nullable = true)
    private Date updateDate;

    @Column(name = "task_deletion_date", nullable = true)
    private Date deletionDate;

    @Column(name = "task_creation_author", nullable = false)
    private Integer creationAuthorId;

    @Column(name = "task_deletion_author", nullable = true)
    private Integer deletionAuthorId;

    @Column(name = "task_aupdate_author", nullable = true)
    private Integer updateAuthorId;
}