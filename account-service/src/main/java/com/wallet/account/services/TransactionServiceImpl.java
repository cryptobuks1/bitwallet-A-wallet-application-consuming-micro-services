package com.wallet.account.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wallet.account.dto.TransactionDTO;

/**
 * Hide the access to the "transaction-service" micro-service inside this local service.
 * 
 * @author Afshar Ahmed
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	private static final Logger LOGGER = Logger.getLogger(TransactionServiceImpl.class.getName());
	
	@Value("${transaction-service-url}")
	private String transactionServiceUri; //TODO static final

//	@LoadBalanced
	@Autowired
	private OAuth2RestTemplate restTemplate;

	protected Logger logger = Logger.getLogger(TransactionServiceImpl.class.getName());

	public TransactionServiceImpl() {
	}

	/**
	 * Returns the transactions for an account. Also applies a circuit breaker that returns
	 * a default value if transaction-service is down.
	 * 
	 * Discussion on HystrixProperty
	 * <ol>
	 * <li><b>execution.isolation.strategy:</b> The value of "SEMAPHORE" enables Hystrix to use 
	 * the current thread for executing this command. Since we need to use the parent HttpRequest 
	 * to pass the OAuth2 access token, we are constrained in using the calling thread. The number 
	 * of concurrent requests that can be made to the command is limited by the semaphore(or counter) 
	 * value; default is 10. In a normal scenario, we would use isolation strategy of "THREAD" that 
	 * executes the Hystrix command in a separate thread pool. This is desirable because it doesn't 
	 * block the calling thread and lets us handle faulty client libraries, client performance 
	 * considerations etc.</li>
	 * 
	 * <li><b>circuitBreaker.requestVolumeThreshold:</b>Sets the min number of requests
	 * in a rolling window that will trip the circuit. For example, if the value is 20, then if only 
	 * 19 requests are received in the rolling window (say a window of 10 seconds) the circuit 
	 * will not trip open even if all 19 failed. We set a lower value here to trip early.</li>
	 * 
	 * <li><b>circuitBreaker.sleepWindowInMilliseconds:</b>Sets the amount of time, after tripping 
	 * the circuit, to reject requests before allowing attempts again to determine if the circuit 
	 * should again be closed. We again have a lower value so that Hystrix tries to pass single
	 * request to the called service quickly.</li>
	 * </ol>
	 * 
	 * @param accountId
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "getFallbackTransactionDTO", 
			commandProperties = {@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
								 @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
								 @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000")})
	public TransactionDTO getTransactionByAccount(Long accountId) {
		/**
		 * We can't use the context root for transaction-service because of the problems getting 
		 * Spring cloud contract to work with it.
		 * 
		 * If we add context-root, then we cannot mock the server on the transaction-service side 
		 * and we will have to make calls with <code>testMode = 'EXPLICIT'</code> for the 
		 * producer tests on the transaction-service side. 
		 * 
		 * This doesn't work on the transaction-service side since there is no mock OAuth2 token that
		 * gets injected there.
		 * 
		 * We can't introduce an annotation on the generated test class since
		 * there is NO original test and hence the @WithMockOAuth2Token
		 * mechanism that we use in the account-service project can't be used in
		 * the transaction-service project.
		 */
//		return restTemplate.getForObject(String.format("http://transaction-service/transaction/%s", accountId),
//				CommentCollectionResource.class);
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info(String.format("Checking transactions for account-id [%s]", accountId));
		}
		String uri = transactionServiceUri + accountId;
		TransactionDTO transaction = null;
		try {
			transaction = restTemplate.getForObject(uri, TransactionDTO.class);
			LOGGER.info(transaction.toString());
		} catch (Exception e) {
			LOGGER.severe("FAILED to get transaction from:"+uri);
		}
		
		return transaction;
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

	@Override
	public void save(TransactionDTO transactionDTO) {
		// TODO Auto-generated method stub
		
	}

}
