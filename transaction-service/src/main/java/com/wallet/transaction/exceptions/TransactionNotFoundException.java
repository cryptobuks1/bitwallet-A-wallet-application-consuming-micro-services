package com.wallet.transaction.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Allow the controller to return a 404 if a transaction is not found by simply
 * throwing this exception. @ResponseStatus causes Spring MVC to return a 404
 * instead of the usual 500.
 * 
 * @author Afshar Ahmed
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TransactionNotFoundException() {}
	
	public TransactionNotFoundException(Long id) {
		super("No Transaction exists for: " + id);
	}
}
