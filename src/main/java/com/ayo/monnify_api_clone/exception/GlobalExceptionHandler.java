package com.ayo.monnify_api_clone.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ayo.monnify_api_clone.auth.AuthenticationException;
import com.ayo.monnify_api_clone.utils.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse>handleAuthenticationException(AuthenticationException exc){
        exc.printStackTrace();
        ApiResponse apiResponse = ApiResponse.builder()
                .requestSuccessful(false)
                .responseCode("99")
                .responseMessage(exc.getMessage())
                .responseBody("")
                .build();
        return ResponseEntity.status(401).body(apiResponse);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ApiResponse> handleInternalServerException(InternalServerException exc){
        exc.printStackTrace();
        ApiResponse apiResponse = ApiResponse.builder()
                .requestSuccessful(false)
                .responseCode("99")
                .responseMessage(exc.getMessage())
                .responseBody("")
                .build();
        return ResponseEntity.status(500).body(apiResponse);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiResponse> handleServiceException(ServiceException exc){
        exc.printStackTrace();
        ApiResponse apiResponse = ApiResponse.builder()
                .requestSuccessful(false)
                .responseCode("99")
                .responseMessage(exc.getMessage())
                .responseBody("")
                .build();
        return ResponseEntity.status(exc.getStatusCode()).body(apiResponse);
    }
    

}
