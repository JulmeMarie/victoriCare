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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "child_care")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChildCare implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_care_id", nullable = false)
    private Long id;

    @Column(name = "child_care_start_date", nullable = true)
    private Date startDate;

    @Column(name = "child_care_end_date", nullable = true)
    private Date endDate;

    @Column(name = "child_care_creation_date", nullable = true)
    private Date creationDate;

    @Column(name = "child_care_update_date", nullable = true)
    private Date updateDate;

    @Column(name = "child_care_deletion_date", nullable = true)
    private Date deletionDate;

    @Column(name = "child_care_update_author", nullable = true)
    private Integer updateAuthorId;

    @Column(name = "child_care_deletion_author", nullable = true)
    private Integer deletionAuthorId;

    @Column(name = "child_care_creation_author", nullable = true)
    private Integer creationAuthorId;

    @Column(name = "child_care_creation_family", nullable = true)
    private Integer creationFamilyId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child_care_baby_sitter", nullable = true)
    private BabySitter babySitter;
}