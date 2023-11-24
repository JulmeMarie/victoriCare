package com.victoricare.api.dtos.inputs;

import com.victoricare.api.validators.ValidEmail;
import com.victoricare.api.validators.ValidPass;
import com.victoricare.api.validators.ValidTrue;
import com.victoricare.api.validators.ValidUsername;
import lombok.Data;

@Data
public class SignUpInputDTO {

	private Integer id;

	@ValidUsername
	private String pseudo;

	@ValidEmail
	private String email;

	@ValidPass
	private String pass;

	@ValidTrue
	private boolean termsOK;
}
