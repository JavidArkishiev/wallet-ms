package com.example.walletms.mapper;

import com.example.walletms.dto.response.BalanceResponseDetails;
import com.example.walletms.entity.Balance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BalanceMapper {
    @Mapping(target = "balance", source = "balance.totalBalance")
    BalanceResponseDetails mapToDto(Balance balance);
}
