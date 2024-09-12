package com.ayo.monnify_api_clone.wallet;

import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.banks.Bank;
import com.ayo.monnify_api_clone.utils.Utils;
import com.ayo.monnify_api_clone.wallet.dto.CreateWalletDto;
import com.ayo.monnify_api_clone.wallet.dto.WalletResponseDto;
import com.rabbitmq.client.Return;

@Service
public class WalletMapper {

    public Wallet toWalletEntity(CreateWalletDto pl, Bank bank){
        Long accountNumber = Utils.generateRandomNumber();
        return Wallet.builder()
                    .accountName(pl.getCustomerName())
                    .accountNumber(accountNumber.toString())
                    .bankName(bank.getName())
                    .walletName(pl.getWalletName())
                    .customerEmail(pl.getCustomerEmail())
                    .customerName(pl.getCustomerName())
                    .contractCode(pl.getContractCode())
                    .walletReference(pl.getWalletReference())
                    .build();
    }


    public BvnDetails toBvnDetailsEntity(String customerEmail, BvnDetails bvnDetails) {
        return BvnDetails.builder()
                .customerEmail(customerEmail)
                .bvn(bvnDetails.getBvn())
                .bvnDateOfBirth(bvnDetails.getBvnDateOfBirth())
                .build();

    }

    public WalletResponseDto toWalletResponseDto(Wallet wallet, BvnDetails bvnDetails) {
        return WalletResponseDto.builder()
                .accountName(wallet.getAccountName())
                .accountNumber(wallet.getAccountNumber())
                .walletName(wallet.getWalletName())
                .customerEmail(wallet.getCustomerEmail())
                .customerName(wallet.getCustomerName())
                .walletReference(wallet.getWalletReference())
                .bvnDetails(bvnDetails)
                .build();
    }

}
