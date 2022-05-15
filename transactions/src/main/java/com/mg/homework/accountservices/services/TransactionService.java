package com.mg.homework.accountservices.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.mg.homework.accountservices.dao.TransactionsRepository;
import com.mg.homework.accountservices.dao.domain.Transaction;
import com.mg.homework.accountservices.dto.TransactionResponse;

import lombok.RequiredArgsConstructor;

/**
 * Transactions business logic
 * @author mike
 *
 */
@Service
@RequiredArgsConstructor
public class TransactionService {
	
	private final TransactionsRepository repository;

	/**
	 * Creates new transaction
	 * @param accountId
	 * @param money
	 */
	public void createTransaction(String accountId, BigDecimal money) {
		repository.save(new Transaction(accountId, money, LocalDateTime.now()));
	}
	
	/**
	 * Fetches all transactions for specified account
	 * @param id - account identifier
	 * @return list of found transactions
	 */
	public List<TransactionResponse> getTransactionsForAccount(String id) {
		try(Stream<Transaction> stream = repository.findByAccountId(id).stream()) {
			return stream.map(t -> new TransactionResponse(t.getId(), t.getAccountId(), t.getMoney(), t.getTimestamp()))
					.collect(Collectors.toList());
		}
	}
	
	
}
