package com.victoricare.api.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class DeletionHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deletion_history_id", nullable = false)
    private Long id;

    @Column(name = "deletion_history_table", nullable = false, length = 50)
    private String table;

    @Column(name = "deletion_history_creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "deletion_history_line", nullable = false, length = 10000)
    private String line;

    @Column(name = "deletion_history_creation_author", nullable = false, length = 10000)
    private Integer creationAuthorId;
}
