package com.victoricare.api.entities;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.ManyToAny;

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
@Table(name = "child_care_task")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChildCareTask implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_care_task_id", nullable = false)
    private Long id;

    @Column(name = "child_care_task_child", nullable = false)
    private Integer childId;

    @Column(name = "child_care_task_task_name", nullable = false, length = 255)
    private String taskName;

    @Column(name = "child_care_task_task_description", nullable = true, length = 500)
    private String taskDescription;

    @Column(name = "child_care_task_update_date", nullable = true)
    private Date aupdateDate;

    @Column(name = "child_care_task_start_date", nullable = true)
    private Date startDate;

    @Column(name = "child_care_task_end_date", nullable = true)
    private Date endDate;

    @Column(name = "child_care_task_creation_date", nullable = true)
    private Date creationDate;

    @Column(name = "child_care_task_deletion_date", nullable = true)
    private Date deletionDate;

    @Column(name = "child_care_task_deletion_author", nullable = true)
    private Integer deletionAuthorId;

    @Column(name = "child_care_task_creation_author", nullable = true)
    private Integer creationAuthorId;

    @Column(name = "child_care_task_update_author", nullable = true)
    private Integer aupdateAuthorId;

    @Column(name = "child_care_task_starting_author", nullable = true)
    private Integer startingAuthorId;

    @Column(name = "child_care_task_ending_author", nullable = true)
    private Integer endingAuthorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child_care_task_child_care", nullable = false)
    private ChildCare childCare;
}