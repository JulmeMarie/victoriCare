package com.victoricare.api.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@Table(name = "person")
@NoArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private Integer id;

    @Column(name = "person_lastname", nullable = true, length = 25)
    private String lastname;

    @Column(name = "person_firstname", nullable = true, length = 25)
    private String firstname;

    @Column(name = "person_gender", nullable = true, length = 2)
    private String gender;

    @Column(name = "person_phone", nullable = true, length = 20)
    private String phone;

    @Column(name = "person_image", nullable = true, length = 50)
    private String image;

    @Column(name = "person_country", nullable = true, length = 25)
    private String country;

    @Column(name = "person_unique_identifier", nullable = false, length = 8)
    private String uniqueIdentifier;

    @Column(name = "person_creation_date", nullable = true)
    private Date creationDate;

    @Column(name = "person_update_date", nullable = true)
    private Date updateDate;

    @Column(name = "person_deletion_date", nullable = true)
    private Date deletionDate;

    @Column(name = "person_roles", nullable = true, length = 100)
    private String roles;

    @Column(name = "person_birthday", nullable = true)
    private Date birthday;

    @Column(name = "person_update_author", nullable = true)
    private Integer updateAuthorId;

    @Column(name = "person_deletion_author", nullable = true)
    private Integer deletionAuthorId;

    @Column(name = "person_creation_author", nullable = true)
    private Integer creationAuthorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_address", nullable = true)
    private Address address;
}