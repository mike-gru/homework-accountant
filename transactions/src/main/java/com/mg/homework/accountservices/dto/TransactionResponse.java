package com.mg.homework.accountservices.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TransactionResponse {
	private Integer id;
	private String accountId;
	private BigDecimal money;
	private LocalDateTime timestamp;
}
