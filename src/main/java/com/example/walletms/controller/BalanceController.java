package com.example.walletms.controller;

import com.example.walletms.dto.request.PaymentRequest;
import com.example.walletms.dto.response.BalanceResponse;
import com.example.walletms.dto.response.BalanceResponseDetails;
import com.example.walletms.dto.response.BaseResponse;
import com.example.walletms.dto.response.TransactionResponseDetails;
import com.example.walletms.service.BalanceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.interceptor.TransactionAttributeSourceAdvisor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("balance")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
public class BalanceController {
    private final BalanceService balanceService;

    @PostMapping("top-up")
    @PreAuthorize("hasAuthority('USER')")
    public BaseResponse<String> topUp(@RequestBody @Valid PaymentRequest paymentRequest,
                                      HttpServletRequest servletRequest) {
        balanceService.topUp(paymentRequest, servletRequest);
        return BaseResponse.success("OK");
    }

    @GetMapping("my-balance")
    @PreAuthorize("hasAuthority('USER')")
    public BaseResponse<BalanceResponse> getMyBalance(HttpServletRequest servletRequest) {
        return BaseResponse.oK(balanceService.getMyBalance(servletRequest));

    }

    @GetMapping("/{balanceId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BaseResponse<BalanceResponseDetails> getBalanceById(@PathVariable UUID balanceId) {
        return BaseResponse.oK(
                balanceService.getBalanceById(balanceId));
    }

    @GetMapping("transaction/{transactionId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BaseResponse<TransactionResponseDetails> getBalanceByTransactionId(@PathVariable UUID transactionId) {
        return BaseResponse.oK(
                balanceService.getBalanceByTransactionId(transactionId));
    }
}
