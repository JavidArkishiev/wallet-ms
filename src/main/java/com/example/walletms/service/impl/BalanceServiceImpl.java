package com.example.walletms.service.impl;

import com.example.walletms.dto.request.PaymentRequest;
import com.example.walletms.dto.response.BalanceResponse;
import com.example.walletms.dto.response.BalanceResponseDetails;
import com.example.walletms.dto.response.TransactionResponseDetails;
import com.example.walletms.dto.request.UserDto;
import com.example.walletms.entity.Balance;
import com.example.walletms.entity.Transaction;
import com.example.walletms.exception.ResourceFoundException;
import com.example.walletms.mapper.BalanceMapper;
import com.example.walletms.repository.BalanceRepository;
import com.example.walletms.repository.TransactionRepository;
import com.example.walletms.service.BalanceService;
import com.example.walletms.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
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
    private final BalanceMapper balanceMapper;
    private final TransactionRepository transactionRepository;

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
    @KafkaListener(topics = "verified-user", groupId = "myVerifyUser")
    @Async
    @Transactional
    public void createNewBalance(UserDto message) {
        System.out.println(message);
        Balance balance = new Balance();
        balance.setAmount(BigDecimal.ZERO);
        balance.setCreateAt(LocalDateTime.now());
        balance.setCurrency(AZN);
        balance.setTotalBalance(BigDecimal.ZERO);
        balance.setPhoneNumber(message.phoneNumber());
        balanceRepository.save(balance);
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

    @Override
    public BalanceResponseDetails getBalanceById(UUID balanceId) {
        var balance = balanceRepository.findById(balanceId)
                .orElseThrow(() -> new ResourceFoundException("Balance not found"));
        return balanceMapper.mapToDto(balance);

    }

    @Override
    public TransactionResponseDetails getBalanceByTransactionId(UUID transactionId) {
        var transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceFoundException("Transaction not found"));
        return balanceMapper.mapToDtoTransaction(transaction);

    }
}
