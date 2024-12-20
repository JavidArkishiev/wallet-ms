package com.example.walletms.controller;

import com.example.walletms.dto.request.PaymentRequest;
import com.example.walletms.dto.response.BalanceResponse;
import com.example.walletms.dto.response.BalanceResponseDetails;
import com.example.walletms.dto.response.BaseResponse;
import com.example.walletms.service.BalanceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("balance")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
public class BalanceController {
    private final BalanceService balanceService;

    @PostMapping("top-up")
    public BaseResponse<String> topUp(@RequestBody @Valid PaymentRequest paymentRequest,
                                      HttpServletRequest servletRequest) {
        balanceService.topUp(paymentRequest, servletRequest);
        return BaseResponse.success("OK");
    }

    @GetMapping("my-balance")
    public BaseResponse<BalanceResponse> getMyBalance(HttpServletRequest servletRequest) {
        return BaseResponse.oK(balanceService.getMyBalance(servletRequest));

    }

    @GetMapping("/{balanceId}")
    public BaseResponse<BalanceResponseDetails> getBalanceById(@PathVariable UUID balanceId) {
        return BaseResponse.oK(
                balanceService.getBalanceById(balanceId));
    }
}
