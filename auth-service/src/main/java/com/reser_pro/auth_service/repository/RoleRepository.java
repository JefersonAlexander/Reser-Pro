package com.reser_pro.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reser_pro.auth_service.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
}
