package com.ayo.monnify_api_clone.banks.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankDto {

    private String name;
    private String code;
    private String baseUssdCode;
    private String ussdTemplate;

}
