package com.microservice.payment.service;

import com.microservice.payment.entity.AuditLog;
import com.microservice.payment.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void log(String action, String referenceId, String details) {
        AuditLog log = new AuditLog(
                action,
                referenceId,
                details,
                LocalDateTime.now()
        );

        auditLogRepository.save(log);
    }
}

