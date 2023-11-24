package com.victoricare.api.dtos.outputs;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;

@Builder
public class ContactOutputDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private String email;

	private String text;

	private String subject;

	private String response;

	private Date creationDate;

	private Date responseDate;

	private UserOutputDTO responseAuthor;

}
