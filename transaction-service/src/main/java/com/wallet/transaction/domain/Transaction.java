package com.wallet.transaction.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity class that represents a transaction in database. 
 * 
 * @author Afshar Ahmed
 */

@Entity
@Table(name = "ttransaction")
//@Builder
//@Data
//@EqualsAndHashCode(exclude={"type"})
public class Transaction implements Serializable { 
	/**
	 * @see 
	 * <a href="https://stackoverflow.com/questions/605828/does-it-matter-what-i-choose-for-serialversionuid-when-extending-serializable-cla">
	 *	 Why use 0L as an initial serialVersionUID?
	 * </a>
	 */
	private static final long serialVersionUID = 0L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	/**
	 * A GU(id) column that represents a GUID that we will add to all objects created on the client, 
	 * and have the server enforce their uniqueness.
	 */
	@NotNull
    @Column(nullable = false, unique = true)
    private String guid;
	
	@NotNull
    @Column(nullable = false)
    private Long accountId;

	@NotNull
    @Column(nullable = false)
    private BigDecimal amount;

	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
    private TransactionType type;  

    @Column(insertable = false, updatable = false)
    private Timestamp createdAt;


    public String getGuid() {
		return guid;
	}
    
    public BigDecimal getAmount() {
		return amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	public Long getAccountId() {
		return accountId;
	}

	public TransactionType getType() {
		return type;
	}

	@Override
	public String toString() {
		return String.format("{id=%s, guid=%s, accountId=%s, amount=$%s, type=%s, createdAt=%s}", 
							id, guid, accountId, amount, type, createdAt);
	}
	
	
}
