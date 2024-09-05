package com.ayo.monnify_api_clone.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.exception.ServiceException;
import com.ayo.monnify_api_clone.transaction.dto.InitTransactionResponseDto;
import com.ayo.monnify_api_clone.transaction.dto.InitializeTransactionDto;
import com.ayo.monnify_api_clone.user.UserEntity;
import com.ayo.monnify_api_clone.user.UserRepository;

import lombok.AllArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    // dependencies
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    // initialize a transaction

    public InitTransactionResponseDto initTransaction(InitializeTransactionDto pl ) {
        // get merchant by conttractcode 
        UserEntity user = userRepository.findByContractCode(Long.parseLong(pl.getContractCode())).orElseThrow(() -> new ServiceException(404, "Merchant with the provided contract code not found"));

        // map payload data to entity
        Transaction transaction = Transaction.builder()
                                            .amount(pl.getAmount())
                                            .apiKey(user.getApiKey())
                                            .contractCode(pl.getContractCode())
                                            .currencyCode(pl.getCurrencyCode())
                                            .customerEmail(pl.getCustomerEmail())
                                            .customerName(pl.getCustomerName())
                                            .merchantName(user.getMerchantName())
                                            .paymentReference(pl.getPaymentReference())
                                            .paymentDescription(pl.getPaymentDescription())
                                            .redirectUrl(pl.getRedirectUrl())
                                            .paymentMethods(pl.getPaymentMethods())
                                            .build();
        
        // save transaction
        transactionRepository.save(transaction);


        InitTransactionResponseDto res = InitTransactionResponseDto.builder()
                                                            .transactionReference(transaction.getTransactionReference())
                                                            .apiKey(transaction.getApiKey())
                                                            .paymentReference(transaction.getPaymentReference())
                                                            .merchantName(transaction.getMerchantName())
                                                            .enabledPaymentMethod(transaction.getPaymentMethods())
                                                            .checkoutUrl(String.format("https://sandbox.sdk.monnify.com/checkout/%s", transaction.getTransactionReference()))
                                                            .build();

        return res;

    }

}