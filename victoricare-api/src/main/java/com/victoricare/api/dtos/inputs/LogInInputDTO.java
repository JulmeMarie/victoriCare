package com.victoricare.api.dtos.inputs;

import lombok.Data;

@Data
public class LogInInputDTO {

	private Long id;

	private String identifier;

	private String pass;

	private boolean isRemember;
}
