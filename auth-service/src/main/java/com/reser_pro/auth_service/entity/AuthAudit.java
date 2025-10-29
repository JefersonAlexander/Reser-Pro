package com.reser_pro.auth_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "auth_audits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long auditId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private LocalDateTime auditDate;
}
