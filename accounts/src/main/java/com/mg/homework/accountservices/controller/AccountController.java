package com.mg.homework.accountservices.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mg.homework.accountservices.dto.AccountRequest;
import com.mg.homework.accountservices.dto.CustomerResponse;
import com.mg.homework.accountservices.service.CustomerService;

import lombok.RequiredArgsConstructor;

/**
 * Main controller for account services
 * @author mike
 *
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
	
	private final CustomerService service; 

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createNewAccount(@RequestBody AccountRequest request) {
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<CustomerResponse> getAll() {
		return service.getCustomers();
	}
}
