package com.mg.homework.accountservices.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mg.homework.accountservices.dao.domain.Customer;
import com.mg.homework.accountservices.dao.repository.CustomerRepository;
import com.mg.homework.accountservices.dto.CustomerResponse;

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

	public List<CustomerResponse> getCustomers() {
		try(Stream<Customer> stream = repository.findAll().stream()) {
			return stream.map(c -> new CustomerResponse(c.getId(), c.getName())).collect(Collectors.toList());
		}
	}
}
