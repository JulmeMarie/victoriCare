package com.victoricare.api.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@AllArgsConstructor // JPA requires a constructor with all fields
@NoArgsConstructor // JPA requires a no-arg constructor
@Builder
@Entity
@Table(name = "log_in")
public class LogIn implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_in_id", nullable = false)
    private Long id;

    @Column(name = "log_in_pseudo", nullable = false, length = 50)
    private String pseudo;

    @Column(name = "log_in_email", nullable = true, length = 50)
    private String email;

    @Column(name = "log_in_browser", nullable = true, length = 100)
    private String browser;

    @Column(name = "log_in_ip", nullable = false, length = 50)
    private String ip;

    @Column(name = "log_in_creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "log_in_expiration_date", nullable = false)
    private Date expirationDate;

    @Column(name = "log_in_update_date", nullable = true)
    private Date updateDate;

    @Column(name = "log_in_deletion_date", nullable = true)
    private Date deletionDate;

    @Column(name = "log_in_token", nullable = true, length = 255)
    private String token;

    @Column(name = "log_in_roles", nullable = true, length = 255)
    private String roles;

    @Column(name = "log_in_rights", nullable = true, length = 255)
    private String rights;

    @Column(name = "log_in_deletion_author", nullable = true)
    private Integer deletionAuthorId;

    @Column(name = "log_in_update_author", nullable = true)
    private Integer updateAuthorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "log_in_creation_author", nullable = true)
    private User creationAuthor;
}