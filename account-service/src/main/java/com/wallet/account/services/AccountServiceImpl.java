package com.wallet.account.services;

import java.util.logging.Logger;

import javax.naming.OperationNotSupportedException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wallet.account.domain.Account;
import com.wallet.account.domain.AccountRepository;
import com.wallet.account.dto.TransactionDTO;

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
		Account account = accountRepository.findById(id);
		return ResponseEntity.ok().body(account);
	}
	
	@Override
	public ResponseEntity<?> addAccount(Account account) {
		accountRepository.save(account);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> withdrawAmount(TransactionDTO transactionDTO) throws Exception {
		Account account = accountRepository.findById(transactionDTO.getAccountId());
		if(account.getBalance().compareTo(transactionDTO.getAmount()) >= 0) {
			account.withdraw(transactionDTO.getAmount());
			accountRepository.save(account);
			transactionService.save(transactionDTO);
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		else {
			LOGGER.severe("Insufficient Balance.");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}

	@Override
	@Transactional
	public ResponseEntity<?> depositAmount(TransactionDTO transactionDTO) throws Exception {
		Account account = accountRepository.findById(transactionDTO.getAccountId());
		account.deposit(transactionDTO.getAmount());
		accountRepository.save(account);
		transactionService.save(transactionDTO);
				
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}
