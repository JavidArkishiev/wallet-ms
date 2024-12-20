package com.example.walletms.dto.response;

import com.example.walletms.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TransactionResponseDetails(
        UUID transactionId,
        TransactionType transactionType,
        String sender,
        String receiver,
        @JsonFormat(pattern = "d MMMM, yyyy, HH:mm")
        LocalDateTime createAt,
        BalanceResponseDetails balanceResponseDetails

) {
}
