package com.reser_pro.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reser_pro.auth_service.DTO.LoginRequest;
import com.reser_pro.auth_service.DTO.LoginResponse;
import com.reser_pro.auth_service.DTO.RegisterRequest;
import com.reser_pro.auth_service.DTO.UserDTO;
import com.reser_pro.auth_service.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Operation(summary = "Login", description = "Retorna el JWT luego de loguearse")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws Exception {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Registro de usuario", description = "Retorna el usuario registrado")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) throws Exception {
        return ResponseEntity.ok(authService.register(request));
    }
}
