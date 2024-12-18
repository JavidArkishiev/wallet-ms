package com.example.walletms.repository;

import com.example.walletms.entity.Balance;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface BalanceRepository extends CrudRepository<Balance, UUID> {
   List<Balance> findByPhoneNumber(String phoneNumber);
}
