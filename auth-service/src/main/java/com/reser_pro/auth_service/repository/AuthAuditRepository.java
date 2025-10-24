package com.reser_pro.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reser_pro.auth_service.entity.AuthAudit;

public interface AuthAuditRepository extends JpaRepository<AuthAudit, Long> {
    
}
