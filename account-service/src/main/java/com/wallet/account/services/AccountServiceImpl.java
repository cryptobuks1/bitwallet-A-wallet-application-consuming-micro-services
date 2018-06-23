package com.wallet.account.services;

import java.util.Optional;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wallet.account.domain.Account;
import com.wallet.account.domain.AccountRepository;
import com.wallet.account.dto.TransactionDTO;
import com.wallet.account.exceptions.AccountNotFoundException;

/**
 * Hide the access to the "transaction-service" micro-service inside this local service.
 * 
 * @author Afshar Ahmed
 */
@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger LOGGER = Logger.getLogger(AccountServiceImpl.class.getName());
	
	private AccountRepository accountRepository;
	
	private TransactionService transactionService;
	
	/**
	 * Instance initializer block for general house-keeping tasks
	 */
	{
		if(accountRepository != null) {
			LOGGER.info("AccountRepository has " + accountRepository.count() + " accounts!");
		}
		else {
			LOGGER.warning("AccountRepository has no accounts!");
		}
	}

	@Autowired
	public AccountServiceImpl(AccountRepository accountRepository, TransactionService transactionService) {
		this.accountRepository = accountRepository;
		this.transactionService = transactionService;
	}


	@Override
	public ResponseEntity<Account> findById(Long id) {
		Optional<Account> account = accountRepository.findById(id);
		Optional.ofNullable(account).orElseThrow(() -> new AccountNotFoundException(id));
		
		return ResponseEntity.ok().body(account.get());
	}
	
	@Override
	public ResponseEntity<?> addAccount(Account account) {
		accountRepository.save(account);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> withdrawAmount(TransactionDTO transactionDTO) {
		Long accountId = transactionDTO.getAccountId();
		Optional<Account> optionalAccount = accountRepository.findById(accountId);
		Optional.ofNullable(optionalAccount).orElseThrow(() -> new AccountNotFoundException(accountId));
		
		//accountRepository.save(transactionDTO); use mapper
		transactionService.save(transactionDTO);
		//TransactionDTO transaction = transactionService.getTransactionByAccount(accountId);	
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Override
	@Transactional
	public ResponseEntity<?> depositAmount(TransactionDTO transactionDTO) {
		return null;
	}
	

	
}
