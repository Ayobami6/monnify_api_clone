package com.ayo.monnify_api_clone.account.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.mapping.Any;

import com.ayo.monnify_api_clone.account.enums.ReservedAccountStatus;
import com.ayo.monnify_api_clone.account.enums.ReservedAccountType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InvoiceResponseDto {

    private String contractCode;
    private String accountReference;
    private String accountName;
    private String accountNumber;
    private String bankName;
    private String bankCode;
    private ReservedAccountStatus status;
    private LocalDateTime createOn;
    private String collectionChannel;
    private String customerEmail;
    private String customerName;
    private String bvn;
    private String nin;
    @Builder.Default
    private String currencyCode = "NGN";
    private Any incomeSplitConfig;
    private boolean restrictPaymentSource;
    private ReservedAccountType reservedAccountType;


}
