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

import com.mg.homework.accountservices.dto.TransactionRequest;
import com.mg.homework.accountservices.dto.TransactionResponse;
import com.mg.homework.accountservices.services.TransactionService;

import lombok.RequiredArgsConstructor;


/**
 * Rest for transactions management
 * @author mike
 *
 */
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionsController {
	
	private final TransactionService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createTransaction(@RequestBody TransactionRequest request) {
		service.createTransaction(request.getAccountId(), request.getMoney());
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<TransactionResponse> getTransactionsForAccount(@PathVariable String id) {
		return service.getTransactionsForAccount(id);
	}

}
