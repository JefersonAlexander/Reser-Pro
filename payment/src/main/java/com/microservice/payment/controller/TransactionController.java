package com.microservice.payment.controller;

import com.microservice.payment.entity.PaymentProcess;
import com.microservice.payment.entity.Transaction;
import com.microservice.payment.service.PaymentService;
import com.microservice.payment.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Transactions", description = "Operaciones relacionadas con la transacciones")
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final PaymentService paymentService;

    @Autowired
    public TransactionController(TransactionService transactionService, PaymentService paymentService) {
        this.transactionService = transactionService;
        this.paymentService = paymentService;
    }

    // Registrar transacción manualmente (para pruebas o integraciones internas)
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestParam String referenceId,
                                               @RequestParam String wompiTransactionId,
                                               @RequestParam String status,
                                               @RequestParam Double amount,
                                               @RequestParam(defaultValue = "COP") String currency) {
        Optional<PaymentProcess> paymentOpt = paymentService.findByReference(referenceId);
        if (paymentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No existe un proceso de pago con esa referencia");
        }

        Transaction transaction = transactionService.registerTransaction(
                paymentOpt.get(), wompiTransactionId, status, amount, currency
        );
        return ResponseEntity.ok(transaction);
    }
}

