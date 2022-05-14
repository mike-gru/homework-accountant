package com.mg.homework.accountservices.dao.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Account information
 * @author mike
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
	@Id
	private String id;
	private BigDecimal credit;
}
