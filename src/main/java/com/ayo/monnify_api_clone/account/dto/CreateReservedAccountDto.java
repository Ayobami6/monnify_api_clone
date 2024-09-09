package com.ayo.monnify_api_clone.account.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateReservedAccountDto {

    @NotEmpty
    private String accountReference;

    @NotEmpty
    private String accountName;

    @NotEmpty
    private String contractCode;

    @NotEmpty
    private String customerEmail;

    @NotEmpty
    private String customerName;

    @NotEmpty
    private String bvn;
    @NotEmpty
    private String nin;

    private boolean getAllAvailableBanks;

    private List<String> preferredBanks;

    private Object incomeSplitConfig;

    private boolean restrictPaymentSource;

    private Object allowedPaymentSource;

}
