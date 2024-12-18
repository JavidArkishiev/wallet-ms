package com.example.walletms.service;

import com.example.walletms.dto.request.TransferRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    void transferToPhoneNumber(TransferRequest request, HttpServletRequest servletRequest);
}
