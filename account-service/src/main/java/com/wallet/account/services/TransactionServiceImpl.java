package com.wallet.account.services;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.transaction.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wallet.account.domain.TransactionDTO;

/**
 * Hide the access to the "transaction-service" micro-service inside this local service.
 * 
 * @author Afshar Ahmed
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	private static final Logger LOGGER = Logger.getLogger(TransactionServiceImpl.class.getName());
	
	/**
	 * URL uses the logical name of transaction-service - upper or lower case doesn't matter.
	 * DiscoveryClient_TRANSACTION-SERVICE/10.1.16.213:transaction-service:8082
	 */
	public static final String TRANSACTION_SERVICE_URL = "http://transaction-service/transaction/";
	
	
	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected Logger logger = Logger.getLogger(TransactionServiceImpl.class.getName());

	public TransactionServiceImpl() {
	}

	/**
	 * The RestTemplate works because it uses a custom request-factory that uses
	 * Ribbon to look-up the service to use. This method simply exists to show
	 * this.
	 */
	@PostConstruct
	public void configureRestTemplate() {
		// Can't do this in the constructor because the RestTemplate injection
		// happens afterwards.
		logger.warning("The RestTemplate request factory is " + restTemplate.getRequestFactory().getClass());
	}

	public TransactionDTO getTransactionById(String transactionId) {
		TransactionDTO transaction = restTemplate.getForObject(TRANSACTION_SERVICE_URL + "{id}", TransactionDTO.class, transactionId);
		LOGGER.info("transaction:"+transaction);
		
		if (transaction == null){
			return null;
		}
			//throw new NotFoundException("");
		else
			return transaction;
	}
}
