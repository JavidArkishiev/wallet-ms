package com.example.walletms.controller;

import com.example.walletms.dto.request.TransferRequest;
import com.example.walletms.dto.response.BaseResponse;
import com.example.walletms.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("transfer")
    public BaseResponse<String> transferToPhoneNumber(@RequestBody @Valid TransferRequest transferRequest,
                                                      HttpServletRequest servletRequest) {
        paymentService.transferToPhoneNumber(transferRequest, servletRequest);
        return BaseResponse.success("Transfer success");
    }
}
