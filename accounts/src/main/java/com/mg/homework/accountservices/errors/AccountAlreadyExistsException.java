package com.mg.homework.accountservices.errors;

@SuppressWarnings("serial")
public class AccountAlreadyExistsException extends Exception {

	public AccountAlreadyExistsException(String message) {
		super(message);
	}

}
