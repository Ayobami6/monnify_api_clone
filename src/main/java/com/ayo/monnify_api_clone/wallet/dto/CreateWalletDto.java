package com.ayo.monnify_api_clone.wallet.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateWalletDto {

    private String walletReference;
    private String walletName;
    private String customerName;
    private Object bvnDetails;
    private String customerEmail;

}
