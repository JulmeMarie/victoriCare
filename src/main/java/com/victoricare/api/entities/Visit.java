package com.victoricare.api.entities;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Visit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVisit;

	@Column(name = "ip_visit", nullable = false)
    private String ipVisit;

	@Column(name = "nav_visit", nullable = false)
    private String navVisit;

	@Column(name = "username_visit", nullable = false)
    private String usernameVisit;

	@Column(name = "content_type_visit", nullable = false)
    private String contentTypeVisit;

	@Column(name = "create_at_visit", nullable = false)
    private Date createAtVisit;
}
