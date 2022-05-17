package com.mg.homework.accountservices.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mg.homework.accountservices.dto.AccountResponse;
import com.mg.homework.accountservices.dto.DepositRequest;
import com.mg.homework.accountservices.dto.TransactionRequest;
import com.mg.homework.accountservices.errors.AccountAlreadyExistsException;
import com.mg.homework.accountservices.errors.AccountNotExistsException;
import com.mg.homework.accountservices.errors.InvalidCustomerIdException;
import com.mg.homework.accountservices.errors.TransactionServiceInegrationException;
import com.mg.homework.accountservices.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Serving web ui content
 * 
 * @author mike
 *
 */
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class WebUIController {

	private static final String TRANSACTION = "transaction";

	private static final String ACCOUNT = "account";

	private static final String CUSTOMERS = "customers";

	private final CustomerService customerService;

	@GetMapping("ui/customers")
	public String getCustommers(Model model) {
		model.addAttribute(CUSTOMERS, customerService.getCustomers());
		return CUSTOMERS;
	}

	@GetMapping("ui/accounts/{id}")
	public String getAccount(@PathVariable String id, Model model)
			throws InvalidCustomerIdException, TransactionServiceInegrationException {
		model.addAttribute(ACCOUNT, customerService.getAccountInfo(id));
		model.addAttribute(TRANSACTION, new TransactionRequest());
		return ACCOUNT;
	}
	
	@PostMapping("ui/deposit")
	public String deposit(@ModelAttribute DepositRequest transaction, Model model) throws InvalidCustomerIdException,
						TransactionServiceInegrationException, AccountAlreadyExistsException, AccountNotExistsException {
		log.info(transaction.getMoney().toString());
		String accountId = transaction.getAccountId();
		log.info(accountId.toString());
		AccountResponse accountInfo = customerService.getAccountInfo(accountId);
		if(accountInfo.getCredit() == null) {
			customerService.createAccount(accountId, transaction.getMoney());
		} else {
			customerService.updateAccount(accountId, transaction.getMoney());
		}
		return "redirect:/ui/customers";
	}
}
