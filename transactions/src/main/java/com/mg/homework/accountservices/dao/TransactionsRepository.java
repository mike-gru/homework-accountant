package com.mg.homework.accountservices.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mg.homework.accountservices.dao.domain.Transaction;

/**
 * Dao methods for transaction management
 * @author mike
 *
 */
@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {
	List<Transaction> findByAccountId(String id);
}
