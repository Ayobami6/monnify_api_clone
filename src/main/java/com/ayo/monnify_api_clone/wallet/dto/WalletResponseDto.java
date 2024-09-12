package com.ayo.monnify_api_clone.wallet.dto;

import com.ayo.monnify_api_clone.wallet.BvnDetails;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletResponseDto {

    private String walletName;
    private String walletReference;
    private String customerName;
    private String customerEmail;
    private String feeBearer;
    private BvnDetails bvnDetails;
    private String accountNumber;
    private String accountName;

}
