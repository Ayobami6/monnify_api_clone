package com.ayo.monnify_api_clone.utils;

import org.springframework.stereotype.Service;

@Service
public class ApiResponseMapper {

    public ApiResponse mapDataToResponse(Object data) {
        return ApiResponse.builder()
                    .requestSuccessful(true)
                    .responseCode("0")
                    .responseMessage("success")
                    .responseBody(data)
                    .build();

    }

}
