package com.microservice.payment.repository;

import com.microservice.payment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByWompiTransactionId(String wompiTransactionId);
}
