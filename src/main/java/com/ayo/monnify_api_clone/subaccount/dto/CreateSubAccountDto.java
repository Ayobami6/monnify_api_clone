package com.ayo.monnify_api_clone.subaccount.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSubAccountDto {

    private String currencyCode;
    @NotEmpty
    private String bankCode;
    @NotEmpty
    private String accountNumber;
    @NotEmpty
    private String email;
    @NotEmpty
    private Double defaultSplitPercentage;
    ;

}
