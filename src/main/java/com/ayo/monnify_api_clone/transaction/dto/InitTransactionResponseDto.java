package com.ayo.monnify_api_clone.transaction.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InitTransactionResponseDto {

    private String transactionReference;
    private String paymentReference;
    private String merchantName;
    private String apiKey;
    private String checkoutUrl;
    private List<String> enabledPaymentMethod;

}
