package com.aninfo.model;

import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;
    
    private Long id;
    private TransactionType transactionType;
    private double amount;

    public Transaction(Long accountId, TransactionType transactionType, double amount) {
        this.id = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    // Getters and setters for the fields

    public Long getAccountId() {
        return id;
    }

    public void setAccountId(Long accountId) {
        this.id = accountId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

