package com.aninfo.model;

import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;
    
    private Long cbu;
    private TransactionType transactionType;
    private Double amount;

    public Transaction(Long accountId, TransactionType transactionType, Double amount) {
        this.cbu = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public Transaction() {
        
    }

    // Getters and setters for the fields

    public Long getAccountId() {
        return cbu;
    }

    public void setAccountId(Long accountId) {
        this.cbu = accountId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

