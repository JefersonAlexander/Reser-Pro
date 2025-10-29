package com.reser_pro.auth_service.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthAuditDTO {
    private Long auditId;
    private UserDTO userDTO;
    private String eventType;
    private String details;
    private LocalDateTime auditDate;
}
