package com.victoricare.api.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // JPA requires a no-arg constructor
@AllArgsConstructor // JPA requires an all-args constructor
@Builder
@Entity
public class Media implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id", nullable = false)
    private Integer id;

    @Column(name = "media_title", nullable = true)
    private String title;

    @Column(name = "media_name", nullable = false)
    private String name;

    @Column(name = "media_folder", nullable = false, length = 50)
    private String folder;

    @Column(name = "media_type", nullable = false, length = 6)
    private String type;

    @Column(name = "media_position", nullable = true, length = 6)
    private String position;

    @Column(name = "media_creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "media_update_date", nullable = true)
    private Date updateDate;

    @Column(name = "media_deletion_date", nullable = true)
    private Date deletionDate;

    @Column(name = "media_creation_author", nullable = false)
    private Integer creationAuthorId;

    @Column(name = "media_deletion_author", nullable = true)
    private Integer deletionAuthorId;

    @Column(name = "media_update_author", nullable = true)
    private Integer updateAuthorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_section", nullable = true)
    private Section section;
}