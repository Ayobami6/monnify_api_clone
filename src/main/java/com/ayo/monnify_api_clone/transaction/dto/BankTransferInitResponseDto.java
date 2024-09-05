package com.ayo.monnify_api_clone.transaction.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankTransferInitResponseDto {

    private String accountNumber;
    private String bankName;
    private String bankCode;
    private String accountName;
    private String accountDurationSeconds;
    private String ussdPayment;
    private Object requestTime;
    private Object expiresOn;
    private String transactionReference;
    private String paymentReference;
    private Double amount;
    private Integer fee;
    private Double totalPayable;
    private String collectionChannel;
    private Object productInformation;

}
