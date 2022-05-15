package com.mg.homework.accountservices.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mg.homework.accountservices.dto.AccountRequest;
import com.mg.homework.accountservices.dto.AccountResponse;
import com.mg.homework.accountservices.dto.CustomerResponse;
import com.mg.homework.accountservices.errors.AccountAlreadyExistsException;
import com.mg.homework.accountservices.errors.InvalidCustomerIdException;
import com.mg.homework.accountservices.errors.TransactionServiceInegrationException;
import com.mg.homework.accountservices.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Main controller for account services
 * 
 * @author mike
 *
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

	private final CustomerService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createNewAccount(@RequestBody AccountRequest request) {
		try {
			service.createAccount(request.getId(), request.getCredit());
		} catch (InvalidCustomerIdException e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (AccountAlreadyExistsException e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		} catch (TransactionServiceInegrationException e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, e.getMessage());
		}
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<CustomerResponse> getAll() {
		return service.getCustomers();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public AccountResponse getAccountInfo(@PathVariable String id) {
		try {
			return service.getAccountInfo(id);
		} catch (InvalidCustomerIdException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
