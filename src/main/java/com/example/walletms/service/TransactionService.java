package com.example.walletms.service;

import com.example.walletms.dto.response.TransactionResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    List<TransactionResponse> getAllHistory(HttpServletRequest servletRequest);

    TransactionResponse getTransactionById(UUID transactionId);
}
