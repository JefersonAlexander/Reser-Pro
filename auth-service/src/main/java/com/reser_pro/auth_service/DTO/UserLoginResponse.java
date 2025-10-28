package com.reser_pro.auth_service.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    private Long userId;
    private String email;
    private String userName;
    private String phoneNumber;
    private List<String> roles = new ArrayList<>();
}
