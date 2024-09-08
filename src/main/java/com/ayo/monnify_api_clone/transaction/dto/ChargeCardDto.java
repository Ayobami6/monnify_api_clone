package com.ayo.monnify_api_clone.transaction.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChargeCardDto {

    private String transactionReference;

    @Builder.Default
    private String collectionChannel = "API_NOTIFICATION";

    private CardDto card;


}
