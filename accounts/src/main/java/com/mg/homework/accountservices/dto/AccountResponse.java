package com.mg.homework.accountservices.dto;

import java.math.BigDecimal;

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
}
