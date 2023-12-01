package com.victoricare.api.entities;
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

@Entity
@Data
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContact;

	@Column(name = "name_contact", nullable = false)
    private String nameContact;

	@Column(name = "email_contact", nullable = false)
	private String emailContact;


	@Column(name = "text_contact", nullable = false)
	private String textContact;

	@Column(name = "subject_contact", nullable = false)
	private String subjectContact;

	@Column(name = "response_contact", nullable = true)
	private String responseContact;

	@Column(name = "create_at_contact", nullable = true)
	private Date createAtContact;

	@Column(name = "response_at_contact", nullable = true)
	private Date responseAtContact;

	@Column(name = "delete_at_contact", nullable = true)
	private Date deleteAtContact;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "response_by_contact", nullable = true)
	private User responseByContact;


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "delete_by_contact", nullable = true)
	private User deleteByContact;
}
