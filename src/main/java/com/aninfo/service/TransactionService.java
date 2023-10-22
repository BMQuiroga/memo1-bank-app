package com.aninfo.service;

import com.aninfo.repository.TransactionRepository;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction findTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public List<Transaction> findTransactionsByAccountId(Long accountId) {
        return transactionRepository.findAllByCbu(accountId);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public void addDeposit(Long cbu, Double Ammount) {
        Transaction depositTransaction = new Transaction(cbu,TransactionType.DEPOSIT,Ammount);
        transactionRepository.save(depositTransaction);
    }

    public void addWithdraw(Long cbu, Double Ammount) {
        Transaction withdrawalTransaction = new Transaction(cbu,TransactionType.WITHDRAW,Ammount);
        transactionRepository.save(withdrawalTransaction);
    }
}

