package com.wallet.account.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.account.domain.Account;
import com.wallet.account.dto.TransactionDTO;
import com.wallet.account.exceptions.AccountNotFoundException;
import com.wallet.account.services.AccountService;

/**
 * A RESTFul controller for accessing account information.
 * 
 * @author Afshar Ahmed
 */
@RestController
public class AccountsController {

	private AccountService accountService;

	@Autowired
	public AccountsController(AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * Fetch a wallet account with the specified owner name.<br><br>
	 * Also provides account balance information.
	 * 
	 * @param ownerName The account's owner name.
	 * @return 	The account if found.
	 * @throws 	AccountNotFoundException, if not found.
	 */
	@RequestMapping(value = "/account/{id}", produces = "application/json; charset=UTF-8")
	public ResponseEntity<Account> getAccount(@PathVariable("id") final Long id) {
		return accountService.findById(id);
	}
	
	/**
	 * Add a wallet account using the details in Account request-body.
	 * 
	 * @param account The account to be added
	 * @return 	ResponseEntity with status 201, if account is created.
	 */
	@RequestMapping(value = "/account/", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
	public ResponseEntity<?> addAccount(@Valid @RequestBody Account account) {
		return accountService.addAccount(account);
	}
	
	/**
	 * REST endpoint to withdraw amount from a given account.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/account/withdraw/", method = RequestMethod.PATCH, produces = "application/json; charset=UTF-8")
	public ResponseEntity<?> withdrawAmount(@Valid @RequestBody TransactionDTO transactionDto) throws Exception {
		return accountService.withdrawAmount(transactionDto);
	}
	
	/**
	 * REST endpoint to deposit amount in a given account.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/account/deposit/", method = RequestMethod.PATCH, produces = "application/json; charset=UTF-8")
	public ResponseEntity<?> depositAmount(@Valid @RequestBody TransactionDTO transactionDto) throws Exception {
		return accountService.depositAmount(transactionDto);
	}
	
}
