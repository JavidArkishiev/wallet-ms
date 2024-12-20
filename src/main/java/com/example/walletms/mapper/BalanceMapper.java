package com.example.walletms.mapper;

import com.example.walletms.dto.response.BalanceResponseDetails;
import com.example.walletms.dto.response.TransactionResponseDetails;
import com.example.walletms.entity.Balance;
import com.example.walletms.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BalanceMapper {
    @Mapping(target = "balance", source = "balance.totalBalance")
    BalanceResponseDetails mapToDto(Balance balance);

    @Mapping(target = "sender", source = "transaction.senderPhoneNumber")
    @Mapping(target = "receiver", source = "transaction.receiverPhoneNumber")
    @Mapping(target = "createAt", source = "transaction.transactionDate")
    @Mapping(target = "balanceResponseDetails", source = "transaction.balance")
    TransactionResponseDetails mapToDtoTransaction(Transaction transaction);
}
