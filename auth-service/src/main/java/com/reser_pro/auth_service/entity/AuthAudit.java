package com.reser_pro.auth_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "auth_audits")
public class AuthAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long auditId;
    
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private LocalDateTime auditDate;
}
