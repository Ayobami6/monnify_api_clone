package com.ayo.monnify_api_clone.transaction;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.transaction.dto.BankTransferInitResponseDto;

@Service
public class TransactionMapper {
    
    // map to transfer dto
    public BankTransferInitResponseDto toBankTransferInitResponseDto(Map<String, Object> data) {
        return BankTransferInitResponseDto.builder()
                .transactionReference(data.get("transactionReference").toString())
                .paymentReference(data.get("paymentReference").toString())
                .amount(Double.parseDouble(data.get("amount").toString()))
                .totalPayable(Double.parseDouble(data.get("amount").toString()))
                .accountDurationSeconds("2345")
                .accountNumber(data.get("accountNumber").toString())
                .accountName(data.get("accountName").toString())
                .bankName(data.get("bankName").toString())
                .bankCode(data.get("bankCode").toString())
                .collectionChannel(data.get("collectionChannel").toString())
                .expiresOn(data.get("expiresOn"))
                .requestTime(data.get("requestTime"))
                .ussdPayment((data.get("ussdPayment")!= null) ? 
                        data.get("ussdPayment").toString() : null)
                .fee( Integer.parseInt(data.get("fee").toString()) )
                .productInformation(null)
                .build();
    }


}
