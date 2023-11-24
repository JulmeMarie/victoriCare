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
@PrimaryKeyJoinColumn(name = "user_id")
@Entity
public class User extends Person {

    @Column(name = "user_pseudo", nullable = true, length = 25)
    private String pseudo;

    @Column(name = "user_email", nullable = false, length = 25)
    private String email;

    @Column(name = "user_pass", nullable = false, length = 255)
    private String pass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_description", nullable = true)
    private Doc description;

    @Column(name = "user_pass_date", nullable = false)
    private Date passDate;

    @Column(name = "user_rights", nullable = false, length = 100)
    private String rights;

    @Column(name = "user_admin_code", nullable = true)
    private Integer adminCode;

    @Column(name = "user_banishment_date", nullable = false)
    private Date banishmentDate;

    @Column(name = "user_account_creation_date", nullable = true)
    private Date accountCreationDate;

    @Column(name = "user_account_update_date", nullable = true)
    private Date accountUpdateDate;

    @Column(name = "user_account_deletion_date", nullable = true)
    private Date accountDeletionDate;

    @Column(name = "user_profiles", nullable = false, length = 30)
    private String profiles;

    @Column(name = "user_sign_up", nullable = true)
    private Integer signUpId;

    @Column(name = "user_banishment_author", nullable = true)
    private Integer banishmentAuthorId;

    @Column(name = "user_deletion_author", nullable = true)
    private Integer deletionAuthorId;
}
