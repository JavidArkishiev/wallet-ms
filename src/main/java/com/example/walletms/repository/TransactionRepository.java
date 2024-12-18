package com.example.walletms.repository;

import com.example.walletms.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends CrudRepository<Transaction, UUID> {

    List<Transaction> findByUserId(String userId);
}
