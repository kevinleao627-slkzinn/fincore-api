package com.kevin.fincore.transaction.dto;

import com.kevin.fincore.transaction.entity.Transaction;
import com.kevin.fincore.transaction.entity.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
    UUID id,
    UUID sourceAccountId,
    UUID destinationAccountId,
    BigDecimal amount,
    TransactionStatus status,
    LocalDateTime createdAt
) {

    public static TransactionResponse fromEntity(Transaction transaction) {
        return new TransactionResponse(
            transaction.getId(),
            transaction.getSourceAccount().getId(),
            transaction.getDestinationAccount().getId(),
            transaction.getAmount(),
            transaction.getStatus(),
            transaction.getCreatedAt()
        );
    }
}
