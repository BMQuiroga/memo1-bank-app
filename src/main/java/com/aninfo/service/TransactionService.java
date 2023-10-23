package com.aninfo.service;

import com.aninfo.model.Constants;
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
        return transactionRepository.findByTransactionId(id).orElse(null);
    }

    public List<Transaction> findTransactionsByAccountId(Long accountId) {
        return transactionRepository.findAllByCbu(accountId);
    }
    
    public void deleteTransaction(Long id) {
        transactionRepository.deleteByTransactionId(id);
    }

    public void addDeposit(Long cbu, Double Ammount) {

        if (Ammount >= Constants.PROMO_MIN_DEPOSIT) {
			Double bonus = Ammount * Constants.PROMO_PERCENTAGE;
			Ammount = (bonus > Constants.PROMO_MAX_GAIN) ? Ammount + Constants.PROMO_MAX_GAIN : Ammount + bonus;
		}

        Transaction depositTransaction = new Transaction(cbu,TransactionType.DEPOSIT,Ammount);
        transactionRepository.save(depositTransaction);
    }

    public void addWithdraw(Long cbu, Double Ammount) {
        Transaction withdrawalTransaction = new Transaction(cbu,TransactionType.WITHDRAW,Ammount);
        transactionRepository.save(withdrawalTransaction);
    }
}

