package com.mg.homework.accountservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;

import com.mg.homework.accountservices.dto.TransactionResponse;
import com.mg.homework.accountservices.errors.TransactionServiceInegrationException;
import com.mg.homework.accountservices.service.TransactionService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
@Slf4j
class TransactionServiceTests {
	
	private static final BigDecimal MONEY = BigDecimal.valueOf(11.55);
	private static final String CLIENT_ID = "client-id";
	
	@Mock private WebClient.Builder wcBuilder;
	@Mock private WebClient client;
	@Mock private WebClient.RequestBodyUriSpec spec;
	@SuppressWarnings("rawtypes")
	@Mock private WebClient.RequestHeadersSpec hspec; 
	@SuppressWarnings("rawtypes")
	@Mock private RequestHeadersUriSpec urispec; 
	@Mock private WebClient.ResponseSpec rspec;
	
	@InjectMocks
	private TransactionService transactionService;
	
	@BeforeEach
	void init() {
		ReflectionTestUtils.setField(transactionService, "transactionsUrl", "url", String.class);;
		when(wcBuilder.build()).thenReturn(client);
		Mockito.reset(spec, hspec, rspec);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testCreateTransactionOk() {
		// arrange
		when(client.post()).thenReturn(spec);
		when(spec.uri(Mockito.anyString())).thenReturn(spec);
		when(spec.body(Mockito.any(Mono.class), Mockito.any(Class.class))).thenReturn(hspec);
		when(hspec.retrieve()).thenReturn(rspec);
		when(rspec.onStatus(Mockito.any(Predicate.class), Mockito.any(Function.class))).thenReturn(rspec);
		when(rspec.bodyToMono(String.class)).thenReturn(Mono.just(""));
		// run
		try {
			transactionService.createTransaction(CLIENT_ID, MONEY);
		} catch (TransactionServiceInegrationException e) {
			log.error(e.getMessage());
		}
		// assert
		assertTrue(true);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testGetTransactionsForAccountOk() {
		// arrange
		TransactionResponse[] responseData = new TransactionResponse[] {
				new TransactionResponse(1, CLIENT_ID, MONEY, LocalDateTime.now())
		};
		when(client.get()).thenReturn(urispec);
		when(urispec.uri(Mockito.anyString(), Mockito.any(Function.class))).thenReturn(spec);
		when(spec.retrieve()).thenReturn(rspec);
		when(rspec.onStatus(Mockito.any(Predicate.class), Mockito.any(Function.class))).thenReturn(rspec);
		when(rspec.bodyToMono(TransactionResponse[].class)).thenReturn(Mono.just(responseData));
		List<TransactionResponse> transactions = List.of();
		// run
		try {
			transactions = transactionService.getTransactionsForAccount(CLIENT_ID);
		} catch (TransactionServiceInegrationException e) {
			log.error(e.getMessage());
		}
		// assert
		assertTrue(transactions.size() == 1);
		assertEquals(transactions.get(0).getAccountId(), CLIENT_ID);
	}
	
	// TODO create tests for error cases

}
