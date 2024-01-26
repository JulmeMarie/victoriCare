package com.victoricare.api.mailers.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.victoricare.api.entities.SignUp;
import com.victoricare.api.mailers.ISignUpMailer;
import java.util.List;

@Component
public class SignUpMailer extends Mailer implements ISignUpMailer {

    @Value("${victoricare.api.signup.timeout.minute}")
    private Integer timeout;

    @Override
    public void sendCode(SignUp signUp) {
        this.mail.setSubject("VICTORICARE : Votre Code de Validation d'inscription");
        this.mail.setTo(List.of(signUp.getEmail()));
        this.mail.getProperties().put("id", signUp.getId());
        this.mail.getProperties().put("pseudo", signUp.getPseudo());
        this.mail.getProperties().put("creationDate", signUp.getCreationDate());
        this.mail.getProperties().put("code", signUp.getCode());
        this.mail.getProperties().put("timeout", timeout);
        this.mail.setTemplate("sign-up-code-template");
        this.sendHtmlMessage();
    }

    @Override
    public void sendConfirmation(SignUp signUp) {
        this.mail.setSubject("VICTORICARE : Confirmation d'inscription");
        this.mail.setTo(List.of(signUp.getEmail()));
        this.mail.getProperties().put("id", signUp.getId());
        this.mail.getProperties().put("pseudo", signUp.getPseudo());
        this.mail.getProperties().put("validationDate", signUp.getValidatingDate());
        this.mail.setTemplate("sign-up-confirmation-template");
        this.sendHtmlMessage();
    }
}
