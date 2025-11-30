package com.reser_pro.auth_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reser_pro.auth_service.DTO.AuthAuditDTO;
import com.reser_pro.auth_service.entity.AuthAudit;
import com.reser_pro.auth_service.entity.User;
import com.reser_pro.auth_service.mappers.AuthAuditMapper;
import com.reser_pro.auth_service.repository.AuthAuditRepository;

@Service
public class AuthAuditService {
    @Autowired
    private AuthAuditRepository auditRepository;

    @Autowired
    private AuthAuditMapper auditMapper;

    public void saveAudit(User user, String eventType, String details) {
        AuthAudit audit = new AuthAudit();

        audit.setUser(user);
        audit.setEventType(eventType);
        audit.setDetails(details);
        audit.setAuditDate(LocalDateTime.now());

        auditRepository.save(audit);
    }

    public AuthAuditDTO getAuditById(Long auditId) {
        return auditMapper.toDTO(auditRepository.findById(auditId)
                    .orElseThrow(() -> new RuntimeException("Audit not found")));
    }

    public List<AuthAuditDTO> getAllAudits() {
        return auditRepository.findAll()
                .stream()
                .map(auditMapper::toDTO)
                .toList();
    }
}
