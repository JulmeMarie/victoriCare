package com.victoricare.api.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@NoArgsConstructor // JPA requires a no-arg constructor
@AllArgsConstructor // JPA requires an all-args constructor
@Builder
@Entity
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Integer id;

    @Column(name = "address_street", nullable = false, length = 100)
    private String street;

    @Column(name = "address_additionnal", nullable = false, length = 255)
    private String addtionnal;

    @Column(name = "address_zipCode", nullable = false, length = 25)
    private String zipCode;

    @Column(name = "address_town", nullable = false, length = 50)
    private String town;

    @Column(name = "address_country", nullable = false, length = 25)
    private String country;

    @Column(name = "address_creation_date", nullable = true)
    private Date creationDate;

    @Column(name = "address_update_date", nullable = true)
    private Date updateDate;

    @Column(name = "address_deletion_date", nullable = true)
    private Date deletionDate;

    @Column(name = "address_creation_author", nullable = true)
    private Integer creationAuthorId;

    @Column(name = "address_update_author", nullable = true)
    private Integer updateAuthorId;

    @Column(name = "address_deletion_author", nullable = true)
    private Integer deletionAuthorId;
}
