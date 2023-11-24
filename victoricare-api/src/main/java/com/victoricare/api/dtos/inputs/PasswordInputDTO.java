package com.victoricare.api.dtos.inputs;

import lombok.Data;

@Data
public class PasswordInputDTO {

	private Integer id;

	private Integer code;

	private String password;

	private String type;
}
