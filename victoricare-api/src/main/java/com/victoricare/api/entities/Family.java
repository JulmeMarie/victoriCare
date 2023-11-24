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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Family implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "family_id", nullable = false)
    private Integer id;

    @Column(name = "family_creation_date", nullable = true)
    private Date creationDate;

    @Column(name = "family_update_date", nullable = true)
    private Date updateDate;

    @Column(name = "family_deletion_date", nullable = true)
    private Date deletionDate;

    @Column(name = "family_creation_author", nullable = false)
    private Integer creationAuthorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "family_parent_one", nullable = true)
    private User parentOne;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "family_parent_two", nullable = true)
    private User parentTwo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "family_baby_sitter", nullable = true)
    private BabySitter babySitter;

    @Column(name = "family_update_author", nullable = true)
    private Integer updateAuthorId;

    @Column(name = "family_deletion_author", nullable = true)
    private Integer deletionAuthorId;

    @OneToMany(mappedBy = "family")
    private Set<Child> children;

    @OneToMany(mappedBy = "attachmentFamily")
    private Set<Child> attachments;
}