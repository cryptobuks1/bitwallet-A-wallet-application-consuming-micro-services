package com.wallet.transaction.controllers;

import java.util.Optional;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.transaction.domain.Transaction;
import com.wallet.transaction.domain.TransactionRepository;
import com.wallet.transaction.exceptions.TransactionNotFoundException;

/**
 * A RESTFul controller for accessing Transaction information.
 * <br><br>
 * This controller does not provides REST endpoints for UPDATE/DELETE operations
 * because a monetary-transaction record should never be updated or deleted.
 * 
 * @author Afshar Ahmed
 */
@RestController
public class TransactionController {

	private static final Logger LOGGER = Logger.getLogger(TransactionController.class.getName());
	
	protected TransactionRepository transactionRepository;
	
	@Autowired
	public TransactionController(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	/**
	 * Fetch a transaction with the specified transaction id.
	 * http://localhost:8082/transaction-service/transaction/1
	 * 
	 * @param id The id of the transaction to be found.
	 * @return 	The transaction if found.
	 */
	@RequestMapping(value = "/transaction/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public ResponseEntity<Transaction> getTransactionDetails(@PathVariable("id") Long id) {
		Optional<Transaction> transaction = transactionRepository.findById(id);
		Optional.ofNullable(transaction)
			.orElseThrow(() -> new TransactionNotFoundException(id));
		
		return ResponseEntity.ok().body(transaction.get());
	}
	
	/**
	 * Perform (add) a transaction with the details in transaction request-body.
	 * 
	 * @param id The id of the transaction to be found.
	 * @return 	The transaction if found.
	 */
	@RequestMapping(value = "/transaction/", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
	public ResponseEntity<?> performTransaction(@Valid @RequestBody Transaction transaction) {
		transactionRepository.save(transaction);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
}
