package com.reser_pro.auth_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditRequest {
    private String email;
    private String userName;
    private String phoneNumber;
}
