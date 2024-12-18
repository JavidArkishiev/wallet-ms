package com.example.walletms.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequest(
        @NotNull(message = "amount can not be null")
        @Positive(message = "amount must be a positive number")
        BigDecimal amount
) {
}
