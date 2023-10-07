package com.victoricare.api.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDTO {

	@Nullable
	private Integer id;

	@Nullable
	private Integer code;

	@NotNull
    private String password;

	@NotNull
	private String type;
}
