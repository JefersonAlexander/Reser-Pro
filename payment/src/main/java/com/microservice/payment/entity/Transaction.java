package com.microservice.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String wompiTransactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_process_id")
    private PaymentProcess paymentProcess;

    @Column(nullable = false)
    private String status; // APPROVED, DECLINED, VOIDED, etc.

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String currency;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    public Transaction(String wompiTransactionId, PaymentProcess paymentProcess, String status, Double amount, String currency, LocalDateTime processedAt) {
        this.id = id;
        this.wompiTransactionId = wompiTransactionId;
        this.paymentProcess = paymentProcess;
        this.status = status;
        this.amount = amount;
        this.currency = currency;
        this.processedAt = processedAt;
    }
}

