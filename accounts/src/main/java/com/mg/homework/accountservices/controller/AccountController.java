package com.mg.homework.accountservices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mg.homework.accountservices.dto.AccountRequest;

/**
 * Main controller for account services
 * @author mike
 *
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createNewAccount(@RequestBody AccountRequest request) {
	}
}
