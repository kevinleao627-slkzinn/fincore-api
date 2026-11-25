package com.kevin.fincore.account.controller;

import com.kevin.fincore.account.dto.AccountResponse;
import com.kevin.fincore.account.entity.Account;
import com.kevin.fincore.account.service.AccountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")

public class AccountController {
    public final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        Account account = accountService.createAccount(userId);
          return ResponseEntity
          .status(HttpStatus.CREATED)
          .body(AccountResponse.fromEntity(account));

    }
     @GetMapping
    public ResponseEntity<List<AccountResponse>> getMyAccounts(
            @AuthenticationPrincipal Jwt jwt) {

        UUID userId = UUID.fromString(jwt.getSubject());

        List<AccountResponse> accounts = accountService
            .getMyAccounts(userId)
            .stream()
            .map(AccountResponse::fromEntity)
            .toList();

        return ResponseEntity.ok(accounts);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getMyAccount(
        @PathVariable UUID id,
        @AuthenticationPrincipal Jwt jwt) {

        UUID userId = UUID.fromString(jwt.getSubject());

        Account account = accountService.getMyAccount(id, userId);

        return ResponseEntity.ok(AccountResponse.fromEntity(account));
}
}
