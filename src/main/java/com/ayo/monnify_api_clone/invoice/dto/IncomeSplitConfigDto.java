package com.ayo.monnify_api_clone.invoice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class IncomeSplitConfigDto {

    private String subAccountCode;
    private Integer splitAmount;
    private Double feePercentage;
    private Boolean feeBearer;
    private Object splitPercentage;

}
