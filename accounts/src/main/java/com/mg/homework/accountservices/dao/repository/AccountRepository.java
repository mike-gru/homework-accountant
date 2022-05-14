package com.mg.homework.accountservices.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mg.homework.accountservices.dao.domain.Account;

@Repository
/**
 * OPerations with Account table
 * @author mike
 *
 */
public interface AccountRepository extends JpaRepository<Account, String> {
}
