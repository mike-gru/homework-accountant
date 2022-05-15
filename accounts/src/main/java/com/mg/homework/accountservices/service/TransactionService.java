package com.mg.homework.accountservices.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import com.mg.homework.accountservices.dto.TransactionRequest;
import com.mg.homework.accountservices.dto.TransactionResponse;
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
	
	/**
	 * Use transactions REST API to create new transaction for account
	 * @param accountId - account identifier
	 * @param money - transfer amount
	 * @throws TransactionServiceInegrationException
	 */
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

	/**
	 * Use transactions REST API to create new transaction for account
	 * @param id - account identifier
	 * @return - list of transaction info
	 * @throws TransactionServiceInegrationException
	 */
	public List<TransactionResponse> getTransactionsForAccount(String id) throws TransactionServiceInegrationException {
		try {
			TransactionResponse[] responseArray = webClientBuilder.build().get()
					.uri(transactionsUrl, uriBuilder -> uriBuilder.path("/{id}").build(id)).retrieve()
					.onStatus(s -> ! s.equals(HttpStatus.OK), r -> r.bodyToMono(String.class).map(TransactionServiceInegrationException::new))
					.bodyToMono(TransactionResponse[].class).block();
			return Arrays.asList(responseArray);
		} catch (WebClientRequestException e) {
			log.error(e.getMessage(), e);
			throw new TransactionServiceInegrationException(e.getMessage(), e);
		}
	}
	
}
