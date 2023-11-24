package com.victoricare.api.mailers;

import com.victoricare.api.entities.User;

public interface IUserMailer {

	public void mailForAccountCreation(User user);

	public void mailForAccountUpdate(User user);

	public void mailForRecoverAccount(User author);

	public void mailForUpdateEmail(User author);

	public void mailForUpdatePassword(User author);

}
