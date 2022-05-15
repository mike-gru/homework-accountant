package com.mg.homework.accountservices.errors;

import org.springframework.web.reactive.function.client.WebClientRequestException;

@SuppressWarnings("serial")
public class TransactionServiceInegrationException extends Exception {

	public TransactionServiceInegrationException(String message) {
		super(message);
	}

	public TransactionServiceInegrationException(String message, WebClientRequestException e) {
		super(message, e);
	}
	
}
