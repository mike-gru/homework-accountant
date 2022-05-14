package com.mg.homework.accountservices.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountRequest {
	private String id;
	private BigDecimal credit;
}
