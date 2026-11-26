package com.kevin.fincore.transaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequest(

    @NotNull(message = "A conta de origem é obrigatória.")
    UUID sourceAccountId,

    @NotNull(message = "A conta de destino é obrigatória.")
    UUID destinationAccountId,

    @NotNull(message = "O valor da transferência é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
    @Digits(integer = 17, fraction = 2,
            message = "O valor deve possuir no máximo duas casas decimais.")
    BigDecimal amount

) {
}
