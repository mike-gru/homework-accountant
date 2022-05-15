package com.mg.homework.accountservices.errors;

@SuppressWarnings("serial")
public class AccountNotExistsException extends Exception {

	public AccountNotExistsException(String message) {
		super(message);
	}

}
