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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
public class Recovery implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recovery_id", nullable = false)
    private Integer id;

    @Column(name = "recovery_email", nullable = false, length = 25)
    private String email;

    @Column(name = "recovery_pseudo", nullable = false, length = 25)
    private String pseudo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recovery_creation_author", nullable = false)
    private User creationAuthor;

    @Column(name = "recovery_creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "recovery_code_date", nullable = false)
    private Date codeDate;

    @Column(name = "recovery_code_expiration_date", nullable = false)
    private Date codeExpirationDate;

    @Column(name = "recovery_validating_date", nullable = true)
    private Date validatingDate;

    @Column(name = "recovery_deletion_date", nullable = true)
    private Date deletionDate;

    @Column(name = "recovery_ip", nullable = false, length = 50)
    private String ip;

    @Column(name = "recovery_browser", nullable = false, length = 100)
    private String browser;

    @Column(name = "recovery_code", nullable = false, length = 6)
    private String code;
}