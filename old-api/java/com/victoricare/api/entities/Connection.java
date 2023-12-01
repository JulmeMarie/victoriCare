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

@Data
@Entity
public class Connection {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idConnection;

	@Column(name = "login_connection", nullable = true)
    private String loginConnection;

	@Column(name = "password_connection", nullable = true)
    private String passwordConnection;

	@Column(name = "browser_connection", nullable = true)
	private String browserConnection;

	@Column(name = "ip_connection", nullable = true)
	private String ipConnection;

	@Column(name = "create_at_connection", nullable = false)
	private Date createAtConnection;

	@Column(name = "update_at_connection", nullable = true)
	private Date updateAtConnection;

	@Column(name = "expire_at_connection", nullable = false)
	private Date expireAtConnection;

	@Column(name = "delete_at_connection", nullable = true)
	private Date deleteAtConnection;

	@Column(name = "token_connection", nullable = true)
	private String tokenConnection;

	@Column(name = "is_remember_connection", nullable = true)
    private boolean isRememberConnection;

	@Column(name = "role_connection", nullable = true)
    private  String roleConnection;

	@Column(name = "right_connection", nullable = true)
    private  String rightConnection;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "create_by_connection", nullable = true)
    private  User createByConnection;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "delete_by_connection", nullable = true)
    private  User deleteByConnection;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "update_by_connection", nullable = true)
    private  User updateByConnection;

}
