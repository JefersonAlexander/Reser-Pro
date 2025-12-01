package com.microservice.payment.controller;

import com.microservice.payment.entity.PaymentProcess;
import com.microservice.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Payments", description = "Operaciones relacionadas con pagos")
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Crear un nuevo proceso de pago
    @PostMapping
    public ResponseEntity<PaymentProcess> createPayment(@RequestParam Double amount,
                                                        @RequestParam(defaultValue = "COP") String currency,
                                                        @RequestParam String paymentMethod) {
        PaymentProcess payment = paymentService.createPaymentProcess(amount, currency, paymentMethod);
        return ResponseEntity.ok(payment);
    }

    // Consultar un proceso de pago por referencia
    @GetMapping("/{referenceId}")
    public ResponseEntity<?> getPayment(@PathVariable String referenceId) {
        Optional<PaymentProcess> payment = paymentService.findByReference(referenceId);
        return payment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar estado manualmente (solo para pruebas o integración interna)
    // @PatchMapping("/{referenceId}/status")
    // public ResponseEntity<PaymentProcess> updateStatus(@PathVariable String referenceId,
    //                                                    @RequestParam String status) {
    //     PaymentProcess updated = paymentService.updateStatus(referenceId, status);
    //     return ResponseEntity.ok(updated);
    // }
}

