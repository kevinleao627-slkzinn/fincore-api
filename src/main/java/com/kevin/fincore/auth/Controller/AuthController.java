package com.kevin.fincore.auth.controller;

import com.kevin.fincore.auth.dto.LoginRequest;
import com.kevin.fincore.auth.dto.LoginResponse;
import com.kevin.fincore.auth.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; 

@RestController
@RequestMapping("/api/auth")
public class AuthController { // Controlador REST, responsável por lidar com requisições HTTP relacionadas à autenticação de usuários.

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login") 
    public ResponseEntity<LoginResponse> login( // Manipula requisições HTTP POST para o endpoint "/api/auth/login", que é usado para autenticar usuários e gerar tokens JWT.
            @Valid @RequestBody LoginRequest request
    ) {
        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }
}

