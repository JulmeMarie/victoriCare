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
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUser;

	@Column(name = "email_user", nullable = false)
    private String emailUser;

	@Column(name = "email_code_at_user", nullable = false)
    private Date emailCodeAtUser;

	@Column(name = "email_code_user", nullable = false)
    private Integer emailCodeUser;

	@Column(name = "email_new_user", nullable = true)
    private String emailNewUser;

	@Column(name = "email_validate_at_user", nullable = true)
    private Date emailValidateAtUser;

	@Column(name = "account_validate_at_user", nullable = true)
    private Date accountValidateAtUser;

	@Column(name = "account_code_user", nullable = false)
    private Integer accountCodeUser;

	@Column(name = "account_code_at_user", nullable = true)
    private Date accountCodeAtUser;

	@Column(name = "username_user", nullable = true)
    private String usernameUser;

	@Column(name = "firstname_user", nullable = false)
    private String firstnameUser;

	@Column(name = "lastname_user", nullable = false)
    private String lastnameUser;

	@Column(name = "description_user", nullable = true)
    private String descriptionUser;

	@Column(name = "password_user", nullable = true)
    private String passwordUser;

	@Column(name = "password_code_user", nullable = true)
    private Integer passwordCodeUser;

	@Column(name = "password_code_at_user", nullable = false)
    private Date passwordCodeAtUser;

	@Column(name = "password_new_user", nullable = true)
    private String passwordNewUser;

	@Column(name = "password_validate_at_user", nullable = true)
    private Date passwordValidateAtUser;

	@Column(name = "role_user", nullable = true)
    private  String roleUser;

	@Column(name = "right_user", nullable = true)
    private  String rightUser;

	@Column(name = "photo_user", nullable = false)
	private String photoUser;

	@Column(name = "is_contact_addressee_user", nullable = true)
	private boolean isContactAddresseeUser;

	@Column(name = "create_at_user", nullable = false)
	private Date createAtUser;

	@Column(name = "delete_at_user", nullable = true)
	private Date deleteAtUser;

	@Column(name = "update_at_user", nullable = true)
	private Date updateAtUser;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "create_by_user", nullable = true)
    private  User createByUser;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "delete_by_user", nullable = true)
    private  User deleteByUser;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "update_by_user", nullable = true)
    private  User updateByUser;

}
