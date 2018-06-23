package com.wallet.account.services;

import org.springframework.http.ResponseEntity;

import com.wallet.account.domain.Account;
import com.wallet.account.dto.TransactionDTO;

public interface AccountService {
	
	public ResponseEntity<Account> findById(Long id);
	
	public ResponseEntity<?> addAccount(Account account);

	public ResponseEntity<?> withdrawAmount(TransactionDTO transactionDTO);
	
	public ResponseEntity<?> depositAmount(TransactionDTO transactionDTO);
	
}
