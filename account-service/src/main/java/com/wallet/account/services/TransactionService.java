package com.wallet.account.services;

import com.wallet.account.domain.TransactionDTO;

public interface TransactionService {

	public TransactionDTO getTransactionById(String transactionId);
}
