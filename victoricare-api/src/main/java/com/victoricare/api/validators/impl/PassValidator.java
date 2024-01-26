package com.victoricare.api.validators.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.victoricare.api.enums.EPattern;
import com.victoricare.api.validators.ValidPass;
import com.victoricare.api.validators.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PassValidator implements ConstraintValidator<ValidPass, String> {

    @Override
    public boolean isValid(String pass, ConstraintValidatorContext context) {

        log.info("Checking for password.");

        if (!Validator.checkNonNullableString(EPattern.PASS, pass)) {
            log.info("Password not valid.");
            return false;
        }
        return true;
    }
}
