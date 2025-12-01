package com.microservice.payment.service;

import com.microservice.payment.entity.PaymentProcess;
import com.microservice.payment.entity.Transaction;
import com.microservice.payment.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AuditService auditService;

    public TransactionService(TransactionRepository transactionRepository, AuditService auditService) {
        this.transactionRepository = transactionRepository;
        this.auditService = auditService;
    }

    @Transactional
    public Transaction registerTransaction(PaymentProcess paymentProcess, String wompiTransactionId,
                                           String status, Double amount, String currency) {

        if (transactionRepository.existsByWompiTransactionId(wompiTransactionId)) {
            throw new IllegalArgumentException("La transacción ya existe en el sistema");
        }

        Transaction transaction = new Transaction(
                wompiTransactionId,
                paymentProcess,
                status,
                amount,
                currency,
                LocalDateTime.now()
        );

        Transaction saved = transactionRepository.save(transaction);
        auditService.log("TRANSACTION_RECORDED", paymentProcess.getReferenceId(),
                "Transacción registrada con ID Wompi: " + wompiTransactionId);
        return saved;
    }
}
