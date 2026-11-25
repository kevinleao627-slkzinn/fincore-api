package com.kevin.fincore.account.dto;

import com.kevin.fincore.account.entity.Account;
import com.kevin.fincore.account.entity.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountResponse( // Responsável por representar a resposta de uma conta, contendo informações como ID, saldo, status e data de criação.
    UUID id,
    BigDecimal balance,
    AccountStatus status,
    LocalDateTime createdAt
) {

    public static AccountResponse fromEntity(Account account) { // Responsavel por criar uma instância de AccountResponse a partir de uma entidade Account, extraindo os valores relevantes da entidade para preencher os campos do DTO.
        return new AccountResponse(
            account.getId(),
            account.getBalance(),
            account.getStatus(),
            account.getCreatedAt()
        );
    }
}

