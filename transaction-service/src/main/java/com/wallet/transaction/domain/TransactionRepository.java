package com.wallet.transaction.domain;

import com.wallet.transaction.domain.Transaction;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository for performing CRUD operations on entity <code>Transaction</code><br>
 * Also supports paging and sorting.
 * 
 * @author Afshar Ahmed
 */
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, String> {

	/**
     * Returns a transaction that matches the given transaction <code>id</code>.
     * 
     * @param id  The transaction id of the transaction to search
     * @return 	A unique transaction or Optional.empty() in case the query executed does not produce a result.
     */
    Optional<Transaction> findById(Long id);
    
    /**
     * Returns a set of transactions that matches the given <code>accountId</code>. <br>
     * And sorted by their creation-time in descending order, i.e. latest first.
     * 
     * @param accountId  The accountId for which the transactions need to be searched
     * @return 	Set of unique transactions
     */
    Set<Transaction> findByAccountIdOrderByCreatedAtDesc(String accountId); //TODO check findByOwnerName
    
    /**
     * Returns a set of transactions that matches the given transaction-type.
     * 
     * @param type  The type of transactions to search
     * @return 	Set of unique transactions
     */
    Set<Transaction> findByType(String type); //TODO change to enum
    
    /**
     * Returns a set of transactions that were performed after the given timestamp.
     * 
     * @param createdAt  The timestamp after which the transactions need to be searched
     * @return 	Set of unique transactions
     */
    Set<Transaction> findByCreatedAtAfter(Timestamp createdAt);
    
    /*
	Optional<Transaction> findById(Long id);
    Set<Transaction> findByAccountIdOrderByCreatedAtDesc(String accountId); 
    Set<Transaction> findByType(String type); 
    Set<Transaction> findByCreatedAtAfter(Timestamp createdAt);
    */
}
