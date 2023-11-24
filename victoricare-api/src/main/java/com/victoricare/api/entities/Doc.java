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
public class Doc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_id", nullable = false)
    private Integer id;

    @Column(name = "doc_title", nullable = false)
    private String title;

    @Column(name = "doc_nature", nullable = false, length = 10)
    private String nature;

    @Column(name = "doc_creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "doc_update_date", nullable = true)
    private Date updateDate;

    @Column(name = "doc_deletion_date", nullable = true)
    private Date deletionDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doc_creation_author", nullable = true)
    private User creationAuthor;

    @Column(name = "doc_deletion_author", nullable = true)
    private Integer deletionAuthorId;

    @Column(name = "doc_update_author", nullable = true)
    private Integer updateAuthorId;

    @OneToMany(mappedBy = "doc")
    private Set<Section> sections;
}