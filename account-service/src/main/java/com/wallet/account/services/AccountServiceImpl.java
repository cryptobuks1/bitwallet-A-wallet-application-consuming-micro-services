package com.wallet.account.services;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wallet.account.controllers.TransactionWrapper;
import com.wallet.account.domain.Account;
import com.wallet.account.domain.AccountRepository;
import com.wallet.account.domain.TransactionDTO;
import com.wallet.account.exceptions.AccountNotFoundException;

/**
 * Hide the access to the "transaction-service" micro-service inside this local service.
 * 
 * @author Afshar Ahmed
 */
@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger LOGGER = Logger.getLogger(AccountServiceImpl.class.getName());
	
	/**
	 * URL uses the logical case-insensitive name of transaction-service.
	 */
	public static final String TRANSACTION_SERVICE_URL = "http://transaction-service/transaction/%s";
	
//	@Autowired
//	private OAuth2RestTemplate restTemplate;
	
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
	public ResponseEntity<Account> findByOwnerName(String ownerName) {
		Optional<Account> account = accountRepository.findByOwner(ownerName);
		Optional.ofNullable(account).orElseThrow(() -> new AccountNotFoundException(ownerName));
		
		return ResponseEntity.ok().body(account.get());
	}
	
	@Override
	public ResponseEntity<?> addAccount(Account account) {
		accountRepository.save(account);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> withdrawAmount(TransactionWrapper transactionWrapper) {
		Account account = new Account(transactionWrapper.getOwner(), 
									  transactionWrapper.getTransactionAmount());
		TransactionDTO transaction = getTransactionById(1l);	 
				//return ResponseEntity.status(HttpStatus.CREATED).build();
		return null;
	}

	@Override
	@Transactional
	public ResponseEntity<?> depositAmount(TransactionWrapper transactionWrapper) {
		return null;
	}
	
	//TODO Configure with OAuthClientConfiguration
	@HystrixCommand(fallbackMethod = "getFallbackTransactionDTO", 
			commandProperties = {@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
								 @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
								 @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000")})
	public TransactionDTO getTransactionById(Long id) {
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info(String.format("Checking transactions for id [%s]", id));
		}
		//return restTemplate.getForObject(String.format(TRANSACTION_SERVICE_URL, id), TransactionDTO.class);
		return null;
	}

	/**
	 * Fallback method. Need to add the suppress warning since the method is not directly used by the class.
	 * @return the default 
	 */
	@SuppressWarnings("unused")
	private TransactionDTO getFallbackTransactionDTO(Long id) {
		TransactionDTO tx = new TransactionDTO();

		return tx;
	}
	
}
