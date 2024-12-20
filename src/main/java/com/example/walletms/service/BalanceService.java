package com.example.walletms.service;

import com.example.walletms.dto.request.PaymentRequest;
import com.example.walletms.dto.response.BalanceResponse;
import com.example.walletms.dto.response.BalanceResponseDetails;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public interface BalanceService {
    void topUp(PaymentRequest paymentRequest, HttpServletRequest servletRequest);

    BalanceResponse getMyBalance(HttpServletRequest servletRequest);

    BalanceResponseDetails getBalanceById(UUID balanceId);
}
