package com.victoricare.api.validators.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.victoricare.api.dtos.inputs.SignUpInputDTO;
import com.victoricare.api.enums.EPattern;
import com.victoricare.api.validators.ValidSignUp;
import com.victoricare.api.validators.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SignUpValidator implements ConstraintValidator<ValidSignUp, SignUpInputDTO> {

    @Override
    public boolean isValid(SignUpInputDTO dto, ConstraintValidatorContext context) {

        log.info("Checking for signUpDTO.");

        if (!Validator.checNullableInteger(dto.getId())) {
            log.info("SignUpDTO.#id not valid.");
            return false;
        }

        if (!Validator.checkNonNullableString(EPattern.USERNAME, dto.getPseudo())) {
            log.info("SignUpDTO.#pseudo not valid.");
            return false;
        }

        if (!Validator.checkNonNullableString(EPattern.EMAIL, dto.getEmail())) {
            log.info("SignUpDTO.#email not valid.");
            return false;
        }

        if (!Validator.checkNonNullableString(EPattern.PASS, dto.getPass())) {
            log.info("SignUpDTO.#pass not valid.");
            return false;
        }

        if (!dto.isTermsOK()) {
            log.info("SignUpDTO.#termsOK not valid.");
            return false;
        }
        return true;
    }
}
