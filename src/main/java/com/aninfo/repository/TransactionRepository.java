package com.aninfo.repository;

import com.aninfo.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    
    Optional<Transaction> findByTransactionId(Long id);

    List<Transaction> findAllByCbu(Long cbu);

    void deleteByTransactionId(Long id);

    Transaction save(Transaction transaction);
}
