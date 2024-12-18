package com.example.walletms.service.impl;

import com.example.walletms.dto.request.PaymentRequest;
import com.example.walletms.dto.response.BalanceResponse;
import com.example.walletms.entity.Balance;
import com.example.walletms.entity.Transaction;
import com.example.walletms.repository.BalanceRepository;
import com.example.walletms.service.BalanceService;
import com.example.walletms.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.walletms.enums.Currency.AZN;
import static com.example.walletms.enums.TransactionType.TOP_UP;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {
    private final JwtService jwtService;
    private final BalanceRepository balanceRepository;

    @Override
    public void topUp(PaymentRequest paymentRequest, HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        var userId = jwtService.extractUserId(token);
        var phoneNumber = jwtService.extractPhoneNumber(token);

        BigDecimal currentTotalBalance = BigDecimal.ZERO;
        var balanceList = balanceRepository.findByPhoneNumber(phoneNumber);
        if (!balanceList.isEmpty()) {
            var lastBalance = balanceList.get(balanceList.size() - 1);
            if (lastBalance.getTotalBalance() != null) {
                currentTotalBalance = lastBalance.getTotalBalance();
            }
        }

        Balance newBalance = new Balance();
        newBalance.setAmount(paymentRequest.amount());
        newBalance.setTotalBalance(currentTotalBalance.add(paymentRequest.amount()));
        newBalance.setCreateAt(LocalDateTime.now());
        newBalance.setPhoneNumber(phoneNumber);
        newBalance.setCurrency(AZN);

        Transaction transaction = new Transaction();
        transaction.setBalance(newBalance);
        transaction.setUserId(userId);

        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TOP_UP);

        newBalance.getTransactions().add(transaction);
        balanceRepository.save(newBalance);
    }

    @Override
    public BalanceResponse getMyBalance(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        var phoneNumber = jwtService.extractPhoneNumber(token);
        var balanceList = balanceRepository.findByPhoneNumber(phoneNumber);
        if (balanceList.isEmpty()) {
            return new BalanceResponse(null, BigDecimal.ZERO, null);
        }

        var lastBalance = balanceList.get(balanceList.size() - 1);

        return new BalanceResponse(lastBalance.getBalanceId(),
                lastBalance.getTotalBalance(),
                lastBalance.getCreateAt());

    }
}
