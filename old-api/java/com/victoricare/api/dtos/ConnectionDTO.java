package com.victoricare.api.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionDTO {

	@Nullable
	private Long id;

	@NotNull
    private String login;

	@NotNull
    private String password;

	@Nullable
    private boolean isRemember;
}
