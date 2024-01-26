package com.victoricare.api.entities;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "child_id")
@Entity
public class Child extends Person {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child_family", nullable = false)
    private Family family;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child_attachment_family", nullable = true)
    private Family attachmentFamily;

    @Column(name = "child_attachment_date", nullable = true)
    private Date attachmentDate;

    @Column(name = "child_detachment_date", nullable = true)
    private Date detachmentDate;

    @Column(name = "child_attachment_author", nullable = true)
    private Integer attachmentAuthorId;

    @Column(name = "child_detachment_author", nullable = true)
    private Integer detachmentAuthorId;
}
