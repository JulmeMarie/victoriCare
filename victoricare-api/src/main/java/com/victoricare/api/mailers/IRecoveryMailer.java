package com.victoricare.api.mailers;

import com.victoricare.api.entities.Recovery;

public interface IRecoveryMailer {

    public void sendCode(Recovery recovery);

    public void sendConfirmation(Recovery recovery);
}
