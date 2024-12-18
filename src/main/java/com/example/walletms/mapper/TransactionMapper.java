package com.example.walletms.mapper;

import com.example.walletms.dto.response.TransactionResponse;
import com.example.walletms.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "sender", source = "senderPhoneNumber")
    @Mapping(target = "receiver", source = "receiverPhoneNumber")
    @Mapping(target = "createAt", source = "transactionDate")
    @Mapping(target = "amount", source = "balance.amount")
    @Mapping(target = "balance", source = "balance.totalBalance")
    TransactionResponse mapToDto(Transaction transaction);

}
