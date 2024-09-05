package com.ayo.monnify_api_clone.transaction.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OneTimeBankTransferDto {

    private String accountNumber;
    private String accountName;
    private String bankName;
    private String bankCode;
    private double amount;
    private Integer fee;
    private double totalPayable;
    private String transactionReference;
    private String paymentReference;
    private LocalDateTime expiresOn;
    private LocalDateTime requestTime;
    private String accountDurationSeconds;
    private String collectionChannel;

    @Builder.Default
    private Object productInfo = null;
    private String ussdPayment;

}
