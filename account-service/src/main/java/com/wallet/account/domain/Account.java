package com.wallet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "taccount")
//@Data
public class Account implements Serializable {

	private static final long serialVersionUID = 0L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(nullable = false)
	private String owner;

	@Column(name = "balanceAmount", nullable = false)
//	@Getter(AccessLevel.NONE)  
	private BigDecimal balance;
	
	public Account(String owner, BigDecimal balance) {
		this.owner = owner;
		this.balance = balance;
	}
	
	// Default constructor to be used by JPA
	public Account() {

	}
    
	public BigDecimal getBalance() {
		return balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	public void withdraw(BigDecimal amount) {
		balance.subtract(amount);
	}

	public void deposit(BigDecimal amount) {
		balance.add(amount);
	}

	@Override
	public String toString() {
		return String.format("{id=%s, owner=%s, balance=$%s}", id, owner, balance);
	}

}
