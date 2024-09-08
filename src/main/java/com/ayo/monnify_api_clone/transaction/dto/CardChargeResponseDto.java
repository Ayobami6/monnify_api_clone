package com.ayo.monnify_api_clone.transaction.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CardChargeResponseDto {

    private String status;
    private String message;
    private String transactionReference;
    private String paymentReference;
    private Integer authorizedAmount;

}
