package com.victoricare.api.dtos.inputs;

import lombok.Data;
import java.util.Date;

@Data
public class ContactInputDTO {

	private Integer id;

	private String name;

	private String email;

	private String text;

	private String subject;

	private String response;

	private Date responseDate;

	private Integer responseAuthorId;
}
