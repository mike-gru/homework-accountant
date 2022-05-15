package com.mg.homework.accountservices.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mg.homework.accountservices.dao.domain.Account;
import com.mg.homework.accountservices.dao.domain.Customer;
import com.mg.homework.accountservices.dao.repository.AccountRepository;
import com.mg.homework.accountservices.dao.repository.CustomerRepository;
import com.mg.homework.accountservices.dto.AccountResponse;
import com.mg.homework.accountservices.dto.CustomerResponse;
import com.mg.homework.accountservices.errors.AccountAlreadyExistsException;
import com.mg.homework.accountservices.errors.InvalidCustomerIdException;
import com.mg.homework.accountservices.errors.TransactionServiceInegrationException;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author mike
 *
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
	
	private final CustomerRepository customerRepository;
	private final AccountRepository accountRepository;
	private final TransactionService transactionService;
	
	
	/**
	 * Generates starting data for demo purposes
	 */
	@Transactional
	public void populateCustomerData() {
		final String[] names = {"Jack London", "Paul Maria", "Jenefer Lopez", "Marko Polo", "Donald Duck",
				"Chuck Norris", "Dulsiney Tabosco", "Margaret Thatcher", "Luce Besson", "Uncle Sam"};
		IntStream.range(0, 10).forEach(i -> {
			Customer customer = new Customer(UUID.randomUUID().toString(), names[i], null);
			customerRepository.save(customer);
		});
	}

	/**
	 * Provides list of all customers without pagination
	 * @return
	 */
	public List<CustomerResponse> getCustomers() {
		try(Stream<Customer> stream = customerRepository.findAll().stream()) {
			return stream.map(c -> new CustomerResponse(c.getId(), c.getName())).collect(Collectors.toList());
		}
	}

	/**
	 * Fetches the consolidated info on customer's account
	 * @param id - customer/account identifier
	 * @return account data
	 * @throws InvalidCustomerIdException
	 */
	public AccountResponse getAccountInfo(String id) throws InvalidCustomerIdException {
		Customer customer = findCustomer(id);
		return new AccountResponse(customer.getId(), customer.getName(), customer.getAccount() == null ? null : customer.getAccount().getCredit());
	}

	/**
	 * Creates new account
	 * @param id - customer identifier UUID
	 * @param credit - sum of initial money
	 * @throws InvalidCustomerIdException
	 * @throws AccountAlreadyExistsException
	 * @throws TransactionServiceInegrationException 
	 */
	@Transactional
	public void createAccount(String id, BigDecimal credit) throws InvalidCustomerIdException, AccountAlreadyExistsException, TransactionServiceInegrationException {
		Customer customer = findCustomer(id);
		if(customer.getAccount() != null) {
			throw new AccountAlreadyExistsException("Customer with id: " + id + " already has account");
		}
		if(!credit.equals(BigDecimal.ZERO)) {
			transactionService.createTransaction(id, credit);
		}
		Account account = new Account(id, credit);
		accountRepository.saveAndFlush(account);
		customer.setAccount(account);
		customerRepository.save(customer);
	}

	private Customer findCustomer(String id) throws InvalidCustomerIdException {
		return customerRepository.findById(id).orElseThrow(() -> new InvalidCustomerIdException("No customer with id: " + id + "was found"));
	}

}
