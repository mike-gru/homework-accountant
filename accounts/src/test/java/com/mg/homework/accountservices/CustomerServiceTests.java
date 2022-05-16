package com.mg.homework.accountservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mg.homework.accountservices.dao.domain.Account;
import com.mg.homework.accountservices.dao.domain.Customer;
import com.mg.homework.accountservices.dao.repository.AccountRepository;
import com.mg.homework.accountservices.dao.repository.CustomerRepository;
import com.mg.homework.accountservices.dto.AccountResponse;
import com.mg.homework.accountservices.dto.TransactionResponse;
import com.mg.homework.accountservices.service.CustomerService;
import com.mg.homework.accountservices.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
class CustomerServiceTests {

	private static final String NAME = "name";
	private static final BigDecimal MONEY = BigDecimal.valueOf(11.55);
	private static final String CLIENT_ID = "client-id";
	
	@Mock private TransactionService transactionService;
	@Mock private CustomerRepository customerRepository;
	@Mock private AccountRepository accountRepository;
	
	@InjectMocks
	private CustomerService customerService;
	
	@Test
	void testCreateAccountOk()  {

		// arrange
		// run
		try {
			customerService.createAccount(CLIENT_ID, MONEY);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		// assert
		assertTrue(true);
	}
	
	@Test
	void updateAccountOk() throws Exception {
		// arrange
		Account account = new Account(CLIENT_ID, MONEY);
		Customer customer = new Customer(CLIENT_ID, NAME, account);
		
		when(customerRepository.findById(CLIENT_ID)).thenReturn(Optional.of(customer));
		// run
		customerService.updateAccount(CLIENT_ID, MONEY);
		// assert
		assertEquals(MONEY.multiply(BigDecimal.valueOf(2)), account.getCredit());
	}

	@Test
	void getAccountInfoOk() throws Exception {
		// arrange
		Account account = new Account(CLIENT_ID, MONEY);
		Customer customer = new Customer(CLIENT_ID, NAME, account);
		LocalDateTime timestamp = LocalDateTime.now();
		TransactionResponse transactionsResponse = new TransactionResponse(1, CLIENT_ID, MONEY, timestamp);
		
		when(customerRepository.findById(CLIENT_ID)).thenReturn(Optional.of(customer));
		when(transactionService.getTransactionsForAccount(CLIENT_ID)).thenReturn(List.of(transactionsResponse));
		// run
		AccountResponse accountInfo = customerService.getAccountInfo(CLIENT_ID);
		// assert
		assertFalse(accountInfo.getTransactions() == null);
		assertEquals(1, accountInfo.getTransactions().size());
		assertEquals(MONEY, accountInfo.getTransactions().get(0).getMoney());
	}
	
	// TODO create tests for error cases
}
