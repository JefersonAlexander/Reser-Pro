package com.reser_pro.auth_service.mappers;

import org.mapstruct.Mapper;

import com.reser_pro.auth_service.DTO.AuthAuditDTO;
import com.reser_pro.auth_service.entity.AuthAudit;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AuthAuditMapper {
    AuthAuditDTO toDTO(AuthAudit entity);
}
