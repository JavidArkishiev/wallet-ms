package com.example.walletms.service;

import com.example.walletms.dto.request.PaymentRequest;
import com.example.walletms.dto.response.BalanceResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface BalanceService {
    void topUp(PaymentRequest paymentRequest, HttpServletRequest servletRequest);

    BalanceResponse getMyBalance(HttpServletRequest servletRequest);
}
