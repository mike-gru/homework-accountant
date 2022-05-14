package com.mg.homework.accountservices.service;

import java.util.UUID;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mg.homework.accountservices.dao.domain.Customer;
import com.mg.homework.accountservices.dao.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author mike
 *
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
	
	private final CustomerRepository repository;
	
	@Transactional
	/**
	 * Generates starting data for demo purposes
	 */
	public void populateCustomerData() {
		final String[] names = {"Jack London", "Paul Maria", "Jenefer Lopez", "Marko Polo", "Donald Duck",
				"Chuck Norris", "Dulsiney Tabosco", "Margaret Thatcher", "Luce Besson", "Uncle Sam"};
		IntStream.range(0, 10).forEach(i -> {
			Customer customer = new Customer(UUID.randomUUID().toString(), names[i]);
			repository.save(customer);
		});
	}
}
