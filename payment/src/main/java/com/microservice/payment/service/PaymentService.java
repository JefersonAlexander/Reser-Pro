package com.microservice.payment.service;

import com.microservice.payment.dto.WompiTransactionRequest;
import com.microservice.payment.dto.WompiTransactionResponse;
import com.microservice.payment.entity.PaymentProcess;
import com.microservice.payment.integration.WompiGatewayAdapter;
import com.microservice.payment.repository.PaymentProcessRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentProcessRepository paymentProcessRepository;
    private final AuditService auditService;

    @Autowired
    public PaymentService(PaymentProcessRepository paymentProcessRepository, AuditService auditService) {
        this.paymentProcessRepository = paymentProcessRepository;
        this.auditService = auditService;
    }

    @Autowired
    private WompiGatewayAdapter wompiGatewayAdapter;

    public WompiTransactionResponse iniciarPago(String referencia, Double monto, String correoCliente) {
        WompiTransactionRequest req = new WompiTransactionRequest(
            referencia,
            "COP",
            (long) (monto * 100),
            correoCliente,
            "PSE"
        );

        return wompiGatewayAdapter.createTransaction(req);
    }


    @Transactional
    public PaymentProcess createPaymentProcess(Double amount, String currency, String paymentMethod) {
        PaymentProcess process = new PaymentProcess(
                generateReference(),
                amount,
                currency,
                "CREATED",
                paymentMethod,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        PaymentProcess saved = paymentProcessRepository.save(process);
        auditService.log("PAYMENT_CREATED", saved.getReferenceId(), "Nuevo proceso de pago creado.");
        return saved;
    }

    @Transactional
    public PaymentProcess updateStatus(String referenceId, String newStatus) {
        Optional<PaymentProcess> processOpt = paymentProcessRepository.findByReferenceId(referenceId);
        if (processOpt.isEmpty()) {
            throw new IllegalArgumentException("No se encontró el proceso de pago con referencia: " + referenceId);
        }

        PaymentProcess process = processOpt.get();
        process.setStatus(newStatus);
        process.setUpdatedAt(LocalDateTime.now());

        auditService.log("PAYMENT_STATUS_UPDATED", process.getReferenceId(),
                "Estado actualizado a: " + newStatus);

        return paymentProcessRepository.save(process);
    }

    public Optional<PaymentProcess> findByReference(String referenceId) {
        return paymentProcessRepository.findByReferenceId(referenceId);
    }

    private String generateReference() {
        return "PAY-" + System.currentTimeMillis();
    }
}

