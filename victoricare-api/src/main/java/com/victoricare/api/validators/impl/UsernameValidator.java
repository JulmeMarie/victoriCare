package com.victoricare.api.validators.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.victoricare.api.enums.EPattern;
import com.victoricare.api.validators.ValidUsername;
import com.victoricare.api.validators.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        log.info("Checking for username.");

        if (!Validator.checkNonNullableString(EPattern.USERNAME, value)) {
            log.info("Username not valid.");
            return false;
        }
        return true;
    }
}
