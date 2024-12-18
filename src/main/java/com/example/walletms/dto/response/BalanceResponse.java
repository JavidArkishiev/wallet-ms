package com.example.walletms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BalanceResponse(
        UUID balanceId,
        BigDecimal balance,
        @JsonFormat(pattern = "d MMMM, yyyy, HH:mm")
        LocalDateTime createAt
) {
}
