package com.ayo.monnify_api_clone.account;

import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.account.dto.CreateReservedAccountDto;
import com.ayo.monnify_api_clone.account.dto.CreateReservedAccountInvoiceDto;
import com.ayo.monnify_api_clone.account.dto.InvoiceResponseDto;
import com.ayo.monnify_api_clone.account.enums.ReservedAccountStatus;



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
                                .getAllAvailableBanks(pl.isGetAllAvailableBanks())
                                .build();
    }

    public ReservedAccount toReservedAccountFromInvoiceDto(CreateReservedAccountInvoiceDto pl) {
        return ReservedAccount.builder()
                                .accountName(pl.getAccountName())
                                .bvn(pl.getBvn())
                                .nin(pl.getNin())
                                .contractCode(pl.getContractCode())
                                .accountReference(pl.getAccountReference())
                                .customerEmail(pl.getCustomerEmail())
                                .customerName(pl.getCustomerName())
                                .status(ReservedAccountStatus.INACTIVE)
                                .build();

    }

    public InvoiceResponseDto toInvoiceResponseDto(ReservedAccount reservedAccount, Account account) {
        return InvoiceResponseDto.builder()
                                .accountName(account.getAccountName())
                                .accountReference(reservedAccount.getAccountReference())
                                .accountNumber(account.getAccountNumber())
                                .bankCode(account.getBankCode())
                                .bankName(account.getBankName())
                                .customerEmail(reservedAccount.getCustomerEmail())
                                .contractCode(reservedAccount.getContractCode())
                                .customerName(reservedAccount.getCustomerName())
                                .bvn(reservedAccount.getBvn())
                                .nin(reservedAccount.getNin())
                                .collectionChannel("RESERVED_ACCOUNT")
                                .status(reservedAccount.getStatus())
                                .createOn(reservedAccount.getCreatedAt())
                                .incomeSplitConfig(reservedAccount.getIncomeSplitConfig())
                                .restrictPaymentSource(reservedAccount.isRestrictPaymentSource())
                                .reservedAccountType(reservedAccount.getReservedAccountType())
                                .build();

    }

    

}
