package com.example.walletms.service.impl;

import com.example.walletms.client.UserClient;
import com.example.walletms.dto.request.TransferRequest;
import com.example.walletms.entity.Balance;
import com.example.walletms.entity.Payment;
import com.example.walletms.entity.Transaction;
import com.example.walletms.enums.Currency;
import com.example.walletms.exception.ResourceExistException;
import com.example.walletms.repository.BalanceRepository;
import com.example.walletms.repository.PaymentRepository;
import com.example.walletms.service.JwtService;
import com.example.walletms.service.PaymentService;
import com.example.walletms.validations.FormatNumber;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.walletms.enums.TransactionType.TRANSFER;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final JwtService jwtService;
    private final BalanceRepository balanceRepository;
    private final FormatNumber formatNumber;
    private final UserClient userClient;

    @Override
    @Transactional
    public void transferToPhoneNumber(TransferRequest transferRequest, HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String userId = jwtService.extractUserId(token);
        var phoneNumber = jwtService.extractPhoneNumber(token);
        var senderBalance = balanceRepository.findByPhoneNumber(phoneNumber);
        if (senderBalance.isEmpty()) {
            throw new ResourceExistException("You have not enough balance");
        }
        var lastSenderBalance = senderBalance.get(senderBalance.size() - 1);
        var lastBalance = lastSenderBalance.getTotalBalance();

        if (transferRequest.amount().compareTo(lastBalance) > 0) {
            throw new ResourceExistException("Amount can not be more than your balance");
        }

        lastSenderBalance = new Balance();
        lastSenderBalance.setTotalBalance(lastBalance.subtract(transferRequest.amount()));
        lastSenderBalance.setAmount(transferRequest.amount());
        lastSenderBalance.setCreateAt(LocalDateTime.now());
        lastSenderBalance.setCurrency(Currency.AZN);
        lastSenderBalance.setPhoneNumber(phoneNumber);

        String formatPhoneNumber = formatNumber.formatPhoneNumber(transferRequest.phoneNumber());
        var userResponse = userClient.getProfileByPhoneNumber(formatPhoneNumber).getData();

        if (userResponse != null) {
            var receiverBalanceList = balanceRepository.findByPhoneNumber(formatPhoneNumber);

            Balance receiverBalance = new Balance();
            receiverBalance.setPhoneNumber(formatPhoneNumber);
            receiverBalance.setAmount(transferRequest.amount());
            receiverBalance.setCurrency(Currency.AZN);
            receiverBalance.setCreateAt(LocalDateTime.now());

            if (!receiverBalanceList.isEmpty()) {
                var lastReceiverBalance = receiverBalanceList.get(receiverBalanceList.size() - 1);
                receiverBalance.setTotalBalance(lastReceiverBalance.getTotalBalance().add(transferRequest.amount()));
            } else {
                receiverBalance.setTotalBalance(transferRequest.amount());
            }
            Transaction receiverTransaction = new Transaction();
            receiverTransaction.setUserId(userResponse.userId());
            receiverTransaction.setBalance(receiverBalance);
            receiverTransaction.setTransactionDate(LocalDateTime.now());
            receiverTransaction.setTransactionType(TRANSFER);
            receiverTransaction.setSenderPhoneNumber(phoneNumber);
            receiverTransaction.setReceiverPhoneNumber(formatPhoneNumber);

            receiverBalance.getTransactions().add(receiverTransaction);
            balanceRepository.save(receiverBalance);


            Payment payment = new Payment();
            payment.setAmount(transferRequest.amount());
            payment.setCreateAt(LocalDateTime.now());
            payment.setPaymentMethod("ONLINE");
            payment.setPhoneNumber(phoneNumber);


            Transaction senderTransaction = new Transaction();
            senderTransaction.setUserId(userId);
            senderTransaction.setBalance(lastSenderBalance);
            senderTransaction.setPayment(payment);
            senderTransaction.setTransactionDate(LocalDateTime.now());
            senderTransaction.setTransactionType(TRANSFER);
            senderTransaction.setSenderPhoneNumber(phoneNumber);
            senderTransaction.setReceiverPhoneNumber(formatPhoneNumber);

            lastSenderBalance.getTransactions().add(senderTransaction);
            balanceRepository.save(lastSenderBalance);
            paymentRepository.save(payment);

        }
    }
}
