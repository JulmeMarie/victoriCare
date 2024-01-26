package com.victoricare.api.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

@Data
@NoArgsConstructor // JPA requires a no-arg constructor
@AllArgsConstructor // JPA requires an all-args constructor
@Builder
@Entity
public class Section implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id", nullable = false)
    private Integer id;

    @Column(name = "section_order", nullable = false)
    private Integer order;

    @Column(name = "section_title", nullable = false)
    private String title;

    @Column(name = "section_text", nullable = true, length = 1000)
    private String text;

    @Column(name = "section_creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "section_update_date", nullable = true)
    private Date updateDate;

    @Column(name = "section_deletion_date", nullable = true)
    private Date deletionDate;

    @Column(name = "section_creation_author", nullable = true)
    private Integer creationAuthorId;

    @Column(name = "section_deletion_author", nullable = true)
    private Integer deletionAuthorId;

    @Column(name = "section_update_author", nullable = true)
    private Integer updateAuthorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_doc", nullable = false)
    private Doc doc;

    @OneToMany(mappedBy = "section")
    private Set<Media> medias;
}
