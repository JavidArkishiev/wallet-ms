package com.example.walletms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequest(
        @NotBlank(message = "phoneNumber can not be null")
        @Schema(example = "51 789 89 89")
        String phoneNumber,
        @NotNull(message = "amount can not be null")
        @Positive(message = "amount must be a positive number")
        BigDecimal amount
) {
}
