package com.wallet.transaction.domain;

/**
 * Enum to provide global transaction-type constant values.
 * <br><br>
 * <code>DEBIT</code> - specifies transactions where amount is withdrawn from an account
 * <br>
 * <code>CREDIT</code> - specifies transactions where amount is deposited from an account
 * 
 * @author Afshar Ahmed
 */
public enum TransactionType {
	DEBIT, CREDIT
}
