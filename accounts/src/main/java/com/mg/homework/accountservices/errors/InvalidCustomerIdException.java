package com.mg.homework.accountservices.errors;

@SuppressWarnings("serial")
public class InvalidCustomerIdException extends Exception {

	public InvalidCustomerIdException(String message) {
		super(message);
	}
	
}
