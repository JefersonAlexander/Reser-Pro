package com.microservice.payment.controller;

import com.microservice.payment.entity.PaymentProcess;
import com.microservice.payment.service.PaymentService;
import com.microservice.payment.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * Controlador que recibe notificaciones desde Wompi.
 * Wompi envía POST con JSON al endpoint configurado en su panel.
 */
@Tag(name = "Wompi Events URL", description = "Notificaciones desde Wompi")
@RestController
@RequestMapping("/api/webhooks/wompi")
@RequiredArgsConstructor
public class WompiWebhookController {

    private final PaymentService paymentService;
    private final TransactionService transactionService;

    @Autowired
    public WompiWebhookController(PaymentService paymentService, TransactionService transactionService) {
        this.paymentService = paymentService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> payload) {
        try {
            // Extraer información del JSON de Wompi
            Map<String, Object> data = (Map<String, Object>) payload.get("data");
            Map<String, Object> transaction = (Map<String, Object>) data.get("transaction");

            String wompiTransactionId = (String) transaction.get("id");
            String status = (String) transaction.get("status");
            Double amountInCents = Double.parseDouble(transaction.get("amount_in_cents").toString());
            String currency = (String) transaction.get("currency");
            String reference = (String) transaction.get("reference");

            // Buscar el proceso de pago en el sistema
            Optional<PaymentProcess> processOpt = paymentService.findByReference(reference);
            if (processOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró proceso de pago con referencia: " + reference);
            }

            PaymentProcess process = processOpt.get();

            // Registrar la transacción y actualizar estado del pago
            transactionService.registerTransaction(
                    process, wompiTransactionId, status, amountInCents / 100.0, currency
            );

            paymentService.updateStatus(reference, status);

            return ResponseEntity.ok("Webhook procesado correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error procesando webhook: " + e.getMessage());
        }
    }
}

