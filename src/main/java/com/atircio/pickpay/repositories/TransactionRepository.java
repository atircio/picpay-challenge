package com.atircio.pickpay.repositories;

import com.atircio.pickpay.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer > {
}
