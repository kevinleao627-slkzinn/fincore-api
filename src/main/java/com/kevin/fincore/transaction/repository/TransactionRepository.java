package com.kevin.fincore.transaction.repository;

import com.kevin.fincore.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findAllBySourceAccountId(UUID accountId);

    List<Transaction> findAllByDestinationAccountId(UUID accountId);
}

