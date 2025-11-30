package com.microservice.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action; // e.g., PAYMENT_CREATED, TRANSACTION_CONFIRMED
    private String referenceId;
    private String details;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public AuditLog() {
    }

    public AuditLog(String action, String referenceId, String details, LocalDateTime createdAt) {
        this.action = action;
        this.referenceId = referenceId;
        this.details = details;
        this.createdAt = createdAt;
    }
}

