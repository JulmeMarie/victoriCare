package com.victoricare.api.mailers;

import com.victoricare.api.entities.SignUp;

public interface ISignUpMailer {

    public void sendCode(SignUp signUp);

    public void sendConfirmation(SignUp signUp);
}
