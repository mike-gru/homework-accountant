package com.mg.homework.accountservices.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountResponse {
	
	private String id;
	private String name;
	private BigDecimal credit;
	private List<TransactionResponse> transactions;	
	
	public AccountResponse(String id, String name, BigDecimal credit) {
		this.id = id;
		this.name = name;
		this.credit = credit;
		transactions = List.of();
	}
}
