package com.mg.homework.accountservices.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TransactionRequest {
	private String accountId;
	private BigDecimal money;
}
