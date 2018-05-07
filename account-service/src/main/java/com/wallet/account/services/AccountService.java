package com.wallet.account.services;

import org.springframework.http.ResponseEntity;

import com.wallet.account.controllers.TransactionWrapper;
import com.wallet.account.domain.Account;

public interface AccountService {
	
	public ResponseEntity<Account> findByOwnerName(String ownerName);
	
	public ResponseEntity<?> addAccount(Account account);

	public ResponseEntity<?> withdrawAmount(TransactionWrapper transactionWrapper);
	
	public ResponseEntity<?> depositAmount(TransactionWrapper transactionWrapper);
	
}
