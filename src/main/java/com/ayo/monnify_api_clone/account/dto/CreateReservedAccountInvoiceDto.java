package com.ayo.monnify_api_clone.account.dto;

import com.ayo.monnify_api_clone.account.enums.ReservedAccountType;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateReservedAccountInvoiceDto {
    @NotEmpty
    private String contractCode;
    @NotEmpty
    private String accountName;

    @Builder.Default
    private String currencyCode = "NGN";

    @NotEmpty
    private String customerEmail;
    @NotEmpty
    private String customerName;
    @Builder.Default
    private ReservedAccountType reservedAccountType = ReservedAccountType.INVOICE;

    private String nin;
    private String bvn;

    @NotEmpty
    private String accountReference;

}
