package com.ayo.monnify_api_clone.transaction.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankTransferDto {

    private String bankCode;
    private String transactionReference;

}
