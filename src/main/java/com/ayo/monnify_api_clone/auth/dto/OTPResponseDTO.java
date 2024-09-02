package com.ayo.monnify_api_clone.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OTPResponseDTO {

    private String otp;

}
