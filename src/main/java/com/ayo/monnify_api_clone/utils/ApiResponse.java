package com.ayo.monnify_api_clone.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private boolean requestSuccessful;
    private String responseMessage;
    private String responseCode;
    private Object responseBody;


}
