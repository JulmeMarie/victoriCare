package com.victoricare.api.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@AllArgsConstructor // JPA requires a constructor with all fields
@NoArgsConstructor // JPA requires a no-arg constructor
@Builder
@Entity
@Table(name = "sign_up")
public class SignUp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sign_up_id", nullable = false)
    private Integer id;

    @Column(name = "sign_up_pseudo", nullable = false, length = 25)
    private String pseudo;

    @Column(name = "sign_up_pass", nullable = false, length = 100)
    private String pass;

    @Column(name = "sign_up_email", nullable = false, length = 25)
    private String email;

    @Column(name = "sign_up_creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "sign_up_terms_ok", nullable = false)
    private boolean termsOk;

    @Column(name = "sign_up_cancelation_date", nullable = true)
    private Date cancelationDate;

    @Column(name = "sign_up_validating_date", nullable = true)
    private Date validatingDate;

    @Column(name = "sign_up_code_date", nullable = false)
    private Date codeDate;

    @Column(name = "sign_up_code_expiration_date", nullable = false)
    private Date codeExpirationDate;

    @Column(name = "sign_up_code", nullable = false, length = 6)
    private String code;

    @Column(name = "sign_up_browser", nullable = true, length = 100)
    private String browser;

    @Column(name = "sign_up_ip", nullable = false, length = 50)
    private String ip;
}