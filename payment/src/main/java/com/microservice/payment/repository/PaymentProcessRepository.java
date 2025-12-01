package com.microservice.payment.repository;

import com.microservice.payment.entity.PaymentProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentProcessRepository extends JpaRepository<PaymentProcess, Long> {
    Optional<PaymentProcess> findByReferenceId(String referenceId);
}

