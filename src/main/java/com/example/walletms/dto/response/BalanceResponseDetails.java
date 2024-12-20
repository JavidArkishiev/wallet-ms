package com.example.walletms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BalanceResponseDetails(
        UUID balanceId,
        BigDecimal amount,
        BigDecimal balance,
        String currency,
        String phoneNumber,
        @JsonFormat(pattern = "d MMMM, yyyy, HH:mm")
        LocalDateTime createAt

) {
}
