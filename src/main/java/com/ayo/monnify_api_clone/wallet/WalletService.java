package com.ayo.monnify_api_clone.wallet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.banks.Bank;
import com.ayo.monnify_api_clone.banks.BankRepository;
import com.ayo.monnify_api_clone.exception.ServiceException;
import com.ayo.monnify_api_clone.wallet.dto.CreateWalletDto;
import com.ayo.monnify_api_clone.wallet.dto.WalletResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final BankRepository bankRepository;
    private final WalletMapper walletMapper;
    private final BvnDetailsRepository bvnDetailsRepository;

    public WalletResponseDto createWallet(CreateWalletDto pl) {
        // generate bank account
        List<Bank> banks = bankRepository.findAll();
        Bank bank = banks.get((int) Math.random() * 5);
        // validate bvn details form bvn service
        // create new bvn details instance
        BvnDetails bvnDetails = walletMapper.toBvnDetailsEntity(pl.getCustomerEmail(), pl.getBvnDetails());
        BvnDetails bvn = bvnDetailsRepository.save(bvnDetails);

        Wallet wallet = walletMapper.toWalletEntity(pl, bank);
        walletRepository.save(wallet);
        return walletMapper.toWalletResponseDto(wallet, bvn);   
    }


    public Page<WalletResponseDto>getAllMerchantWallet(Pageable pageable) {
        // gets all merchant created wallets
        Page<Wallet> pages = walletRepository.findAll(pageable);

        List<WalletResponseDto> response = new ArrayList<WalletResponseDto>();

        for (Wallet wallet : pages.getContent()) {
            WalletResponseDto responseDto = toResponseDto(wallet);
            response.add(responseDto);
        }

        return new PageImpl<>(response, pages.getPageable(), pages.getTotalElements());

    }


    private WalletResponseDto toResponseDto(Wallet wallet) {
        BvnDetails bvnDetails = bvnDetailsRepository.findByCustomerEmail((wallet.getCustomerEmail())).orElse(null);
        return walletMapper.toWalletResponseDto(wallet, bvnDetails);
    }

}
