package com.mg.homework.accountservices.dao.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transaction table data
 * @author mike
 *
 */
@Entity
@Data
@NoArgsConstructor
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String accountId;
	private BigDecimal money;
	private LocalDateTime timestamp;

	public Transaction(String accountId, BigDecimal money, LocalDateTime timestamp) {
		this.accountId = accountId;
		this.money = money;
		this.timestamp = timestamp;
	}
}
