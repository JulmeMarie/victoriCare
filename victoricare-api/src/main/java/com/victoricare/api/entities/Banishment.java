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
public class Banishment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banishment_id", nullable = false)
    private Integer id;

    @Column(name = "banishment_ip", nullable = false, length = 50)
    private String ip;

    @Column(name = "banishment_browser", nullable = true, length = 100)
    private String browser;

    @Column(name = "banishment_creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "banishment_deletion_date", nullable = true)
    private Date deletionDate;

    @Column(name = "banishment_deletion_author", nullable = true)
    private Integer deletionAuthorId;
}
