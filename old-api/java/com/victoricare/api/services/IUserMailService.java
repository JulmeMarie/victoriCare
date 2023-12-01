package com.victoricare.api.services;
import com.victoricare.api.entities.User;

public interface IUserMailService{

	public void mailForAccountCreation(User user);

	public void mailForAccountUpdate(User user);

	public void mailForRecoverAccount(User author);

	public void mailForUpdateEmail(User author);

	public void mailForUpdatePassword(User author);

}
