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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // JPA requires a no-arg constructor
@AllArgsConstructor // JPA requires an all-args constructor
@Builder
@Entity
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id", nullable = false)
    private Integer id;

    @Column(name = "contact_creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "contact_email", nullable = false, length = 25)
    private String email;

    @Column(name = "contact_name", nullable = false, length = 50)
    private String name;

    @Column(name = "contact_subject", nullable = false, length = 50)
    private String subject;

    @Column(name = "contact_text", nullable = false, length = 1000)
    private String text;

    @Column(name = "contact_response", nullable = true, length = 1000)
    private String response;

    @Column(name = "contact_status", nullable = false, length = 10)
    private String status;

    @Column(name = "contact_ip", nullable = false, length = 50)
    private String ip;

    @Column(name = "contact_browser", nullable = false, length = 100)
    private String browser;

    @Column(name = "contact_response_date", nullable = true)
    private Date responseDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_response_author", nullable = true)
    private User responseAuthor;
}
