package com.wallet.account.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonRootName;


/**
 * Transaction DTO - used to interact with the {@link transaction-service}.
 * 
 * @author Afshar Ahmed
 */
@JsonRootName("Transaction")
public class TransactionDTO { 
    private Long id;
    private String guid;
    private Long accountId;
    private BigDecimal amount;
    private TransactionType type;  
    private Timestamp createdAt;

    /**
	 * Default constructor for JPA.
	 */
    public TransactionDTO() {
    	
	}
    
    public BigDecimal getAmount() {
		return amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}
    
    public String getGuid() {
		return guid;
	}

	public Long getAccountId() {
		return accountId;
	}

	public TransactionType getType() {
		return type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return String.format("{id=%s, accountId=%s, amount=$%s, type=%s, createdAt=%s}", 
								id, accountId, amount, type, createdAt);
	}
	
	/**
	 * Enum to provide global transaction-type constant values.
	 * <br><br>
	 * <code>DEBIT</code> - specifies transactions where amount is withdrawn from an account
	 * <br>
	 * <code>CREDIT</code> - specifies transactions where amount is deposited from an account
	 * 
	 * @author Afshar Ahmed
	 */
	private static enum TransactionType {
		DEBIT, CREDIT
	}
}
