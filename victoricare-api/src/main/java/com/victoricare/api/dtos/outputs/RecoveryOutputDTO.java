package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;

@Builder
public class RecoveryOutputDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String email;

	private String pseudo;

	private Date creationDate;

	private Date codeExpirationDate;

	private Date validatingDate;

	private Date deletionDate;

	private String ip;

	private String browser;

	private String code;
}
