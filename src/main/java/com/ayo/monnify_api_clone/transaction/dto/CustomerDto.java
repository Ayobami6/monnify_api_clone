package com.ayo.monnify_api_clone.transaction.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerDto {
    private String email;
    private String name;
    private String merchantCode;

}
