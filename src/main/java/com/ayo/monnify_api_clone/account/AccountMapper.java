package com.ayo.monnify_api_clone.account;

import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.account.dto.CreateReservedAccountDto;



@Service
public class AccountMapper {

    public ReservedAccount toReservedAccount(CreateReservedAccountDto pl) {
        return ReservedAccount.builder()
                                .accountName(pl.getAccountName())
                                .accountReference(pl.getAccountReference())
                                .contractCode(pl.getContractCode())
                                .bvn(pl.getBvn())
                                .nin(pl.getNin())
                                .customerEmail(pl.getCustomerEmail())
                                .customerName(pl.getCustomerName())
                                .build();
    }

    

}
