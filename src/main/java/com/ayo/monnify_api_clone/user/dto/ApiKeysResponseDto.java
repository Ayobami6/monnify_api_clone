package com.ayo.monnify_api_clone.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiKeysResponseDto {
    private String apiKey;
    private String secretKey;

}
