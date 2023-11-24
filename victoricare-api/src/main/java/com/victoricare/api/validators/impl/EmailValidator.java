package com.victoricare.api.validators.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.victoricare.api.enums.EPattern;
import com.victoricare.api.validators.ValidEmail;
import com.victoricare.api.validators.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        log.info("Checking for e-mail.");

        if (!Validator.checkNonNullableString(EPattern.EMAIL, email)) {
            log.info("SignUpDTO.#email not valid.");
            return false;
        }
        return true;
    }
}
