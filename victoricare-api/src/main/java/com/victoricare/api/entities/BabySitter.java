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
import lombok.*;

@Data
@NoArgsConstructor // JPA requires a no-arg constructor
@AllArgsConstructor // JPA requires an all-args constructor
@Builder
@Entity
public class BabySitter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "baby_sitter_id", nullable = false)
    private Long id;

    @Column(name = "baby_sitter_code", nullable = false, length = 8)
    private String code;

    @Column(name = "baby_sitter_firstname", nullable = false, length = 50)
    private String firstname;

    @Column(name = "baby_sitter_lastname", nullable = false, length = 50)
    private String lastname;

    @Column(name = "baby_sitter_creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "baby_sitter_update_date", nullable = true)
    private Date updateDate;

    @Column(name = "baby_sitter_deletion_date", nullable = true)
    private Date deletionDate;

    @Column(name = "baby_sitter_user", nullable = false)
    private Integer userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "baby_sitter_family", nullable = false)
    private Family family;

    @Column(name = "baby_sitter_update_author", nullable = true)
    private Integer updateAuthorId;

    @Column(name = "baby_sitter_deletion_author", nullable = true)
    private Integer deletionAuthorId;

    @Column(name = "baby_sitter_creation_author", nullable = false)
    private Integer creationAuthorId;
}
