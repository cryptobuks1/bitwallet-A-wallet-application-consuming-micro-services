package com.wallet.account.controllers;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * 
 * {"owner":"dave", "guid":"1211", "transactionAmount":200.65}
 * 
 * @author Afshar
 */
public class TransactionWrapper {
	@NotNull
	private String owner;
	
	@NotNull
	private String guid;
	
	@NotNull
	private BigDecimal transactionAmount;
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	@Override
	public String toString() {
		return String.format("{guid=%s, owner=%s, transactionAmount=$%s}", guid, owner, transactionAmount);
	}
}
