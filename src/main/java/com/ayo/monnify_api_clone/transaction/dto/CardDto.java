package com.ayo.monnify_api_clone.transaction.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDto {
    
    private String number;
    private String expiryMonth;
    private String expiryYear;
    private String pin;
    private String cvv;

}
