package com.kevin.fincore.transaction.controller;

import com.kevin.fincore.transaction.dto.TransactionRequest;
import com.kevin.fincore.transaction.dto.TransactionResponse;
import com.kevin.fincore.transaction.service.TransactionService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        UUID userId = UUID.fromString(jwt.getSubject());

        TransactionResponse response =
                transactionService.createTransaction(request, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
