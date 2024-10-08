package com.ayo.monnify_api_clone.wallet.dto;

import com.ayo.monnify_api_clone.wallet.BvnDetails;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateWalletDto {

    @NotEmpty
    private String walletReference;
    @NotEmpty
    private String walletName;
    @NotEmpty
    private String customerName;
    private BvnDetails bvnDetails;
    @NotEmpty
    private String customerEmail;
    @NotEmpty
    private String contractCode;

}
