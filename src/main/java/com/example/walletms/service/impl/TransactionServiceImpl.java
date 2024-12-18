package com.example.walletms.service.impl;

import com.example.walletms.dto.response.TransactionResponse;
import com.example.walletms.exception.ResourceFoundException;
import com.example.walletms.mapper.TransactionMapper;
import com.example.walletms.repository.TransactionRepository;
import com.example.walletms.service.JwtService;
import com.example.walletms.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final JwtService jwtService;
    private final TransactionMapper transactionMapper;

    @Override
    public List<TransactionResponse> getAllHistory(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String userId = jwtService.extractUserId(token);
        var transactionList = transactionRepository.findByUserId(userId);
        if (transactionList.isEmpty()) {
            return Collections.emptyList();
        }
        return transactionList.stream()
                .sorted((o1, o2) -> o2.getTransactionDate().compareTo(o1.getTransactionDate()))
                .map(transaction -> new TransactionResponse(
                        transaction.getTransactionId(),
                        transaction.getTransactionType(),
                        transaction.getSenderPhoneNumber(),
                        transaction.getReceiverPhoneNumber(),
                        transaction.getBalance().getAmount(),
                        transaction.getBalance().getTotalBalance(),
                        transaction.getTransactionDate()
                )).toList();
    }

    @Override
    public TransactionResponse getTransactionById(UUID transactionId) {
        var transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceFoundException("Transaction not found"));
        return transactionMapper.mapToDto(transaction);
    }
}
