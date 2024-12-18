package com.example.walletms.controller;

import com.example.walletms.dto.response.BaseResponse;
import com.example.walletms.dto.response.TransactionResponse;
import com.example.walletms.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("history")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
public class HistoryController {
    private final TransactionService transactionService;

    @GetMapping
    public BaseResponse<List<TransactionResponse>> getAllHistory(HttpServletRequest servletRequest) {
        return BaseResponse.oK(transactionService.getAllHistory(servletRequest));

    }

    @GetMapping("transaction/{transactionId}")
    public BaseResponse<TransactionResponse> getTransactionById(@PathVariable UUID transactionId) {
        return BaseResponse
                .oK(transactionService.getTransactionById(transactionId));

    }

}
