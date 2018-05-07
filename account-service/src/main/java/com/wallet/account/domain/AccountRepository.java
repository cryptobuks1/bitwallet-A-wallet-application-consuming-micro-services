package com.wallet.account.domain;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Serializable> {
	
	/**
     * Returns an account that matches the given <code>owner</code> name.
     * 
     * @param id  The transaction id of the transaction to search
     * @return 	A unique transaction or Optional.empty() in case the query executed does not produce a result.
     */
    Optional<Account> findByOwner(String owner);
}
