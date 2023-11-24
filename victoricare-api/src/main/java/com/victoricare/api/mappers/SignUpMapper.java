package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.SignUpOutputDTO;
import com.victoricare.api.entities.SignUp;

public class SignUpMapper {

    public static SignUpOutputDTO toOutput(SignUp signUp) {
        if (signUp == null)
            return null;

        return SignUpOutputDTO.builder()
                .id(signUp.getId())
                .pseudo(signUp.getPseudo())
                .email(signUp.getEmail())
                .creationDate(signUp.getCreationDate())
                .termsOk(signUp.isTermsOk())
                .creationDate(signUp.getCreationDate())
                .cancelationDate(signUp.getCancelationDate())
                .validatingDate(signUp.getValidatingDate())
                .codeDate(signUp.getCodeDate())
                .browser(signUp.getBrowser())
                .ip(signUp.getIp())
                .build();
    }
}