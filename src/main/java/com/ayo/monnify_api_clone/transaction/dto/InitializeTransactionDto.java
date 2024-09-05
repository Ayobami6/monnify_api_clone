package com.ayo.monnify_api_clone.transaction.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitializeTransactionDto {

    @NotEmpty
    private String amount;

    @NotEmpty
    private String customerName;

    @NotEmpty
    private String customerEmail;

    @NotEmpty
    private String paymentReference;

    @NotEmpty
    private String paymentDescription;

    @NotEmpty
    private String currencyCode;

    private String redirectUrl;

    @NotEmpty
    private String contractCode;

    @NotEmpty
    private List<String> paymentMethods;
}
