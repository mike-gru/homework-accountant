/**
 * 
 */
package com.mg.homework.accountservices.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mg.homework.accountservices.dao.domain.Customer;

/**
 * Operations with Customer table
 * @author mike
 *
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
