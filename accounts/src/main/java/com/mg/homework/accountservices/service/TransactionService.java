package com.mg.homework.accountservices.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import com.mg.homework.accountservices.dto.TransactionRequest;
import com.mg.homework.accountservices.errors.TransactionServiceInegrationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Provides integration with transactions service 
 * @author mike
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
	
	private final WebClient.Builder webClientBuilder;
	
	@Value("${transactions.url}")
	private String transactionsUrl;
	
	public void createTransaction(String accountId, BigDecimal money) throws TransactionServiceInegrationException {
		TransactionRequest transactionRequest = new TransactionRequest(accountId, money);
		try {
			webClientBuilder.build().post()
					.uri(transactionsUrl)
					.body(Mono.just(transactionRequest), TransactionRequest.class)
					.retrieve()
					.onStatus(s -> ! s.equals(HttpStatus.CREATED), r -> r.bodyToMono(String.class).map(TransactionServiceInegrationException::new))
					.bodyToMono(String.class).block();
		} catch (WebClientRequestException e) {
			log.error(e.getMessage(), e);
			throw new TransactionServiceInegrationException(e.getMessage(), e);
		}
	}
	
}
