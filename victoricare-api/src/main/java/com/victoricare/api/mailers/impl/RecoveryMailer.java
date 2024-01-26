package com.victoricare.api.mailers.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.victoricare.api.entities.Recovery;
import com.victoricare.api.mailers.IRecoveryMailer;
import java.util.List;

@Component
public class RecoveryMailer extends Mailer implements IRecoveryMailer {

    @Value("${victoricare.api.recovery.timeout.minute}")
    private Integer timeout;

    @Override
    public void sendCode(Recovery recovery) {
        this.mail.setSubject("VICTORICARE : Votre Code de Validation de récupération de compte");
        this.mail.setTo(List.of(recovery.getEmail()));
        this.mail.getProperties().put("id", recovery.getId());
        this.mail.getProperties().put("pseudo", recovery.getPseudo());
        this.mail.getProperties().put("creationDate", recovery.getCreationDate());
        this.mail.getProperties().put("code", recovery.getCode());
        this.mail.getProperties().put("timeout", timeout);
        this.mail.setTemplate("recovery-code-template");
        this.sendHtmlMessage();
    }

    @Override
    public void sendConfirmation(Recovery recovery) {
        this.mail.setSubject("VICTORICARE : Confirmation de récupération de compte");
        this.mail.setTo(List.of(recovery.getEmail()));
        this.mail.getProperties().put("id", recovery.getId());
        this.mail.getProperties().put("pseudo", recovery.getPseudo());
        this.mail.getProperties().put("validationDate", recovery.getValidatingDate());
        this.mail.setTemplate("recovery-confirmation-template");
        this.sendHtmlMessage();
    }
}
