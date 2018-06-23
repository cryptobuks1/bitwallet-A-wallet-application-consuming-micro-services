package com.wallet.account.services;

import com.wallet.account.dto.TransactionDTO;

public interface TransactionService {

	public TransactionDTO getTransactionByAccount(Long accountId);
	
	public void save(TransactionDTO transactionDTO);
}
