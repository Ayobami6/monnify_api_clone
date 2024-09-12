package com.ayo.monnify_api_clone.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse> handleNoResourceFoundException(NoResourceFoundException exc){
        exc.printStackTrace();
        ApiResponse apiResponse = ApiResponse.builder()
                .requestSuccessful(false)
                .responseCode("99")
                .responseMessage("Resource not found")
                .responseBody("")
                .build();
        return ResponseEntity.status(404).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException exc){
        exc.printStackTrace();
        ApiResponse apiResponse = ApiResponse.builder()
                .requestSuccessful(false)
                .responseCode("99")
                .responseMessage(exc.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                .responseBody("")
                .build();
        return ResponseEntity.status(400).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleOtherException(Exception exc){
        exc.printStackTrace();
        ApiResponse apiResponse = ApiResponse.builder()
                .requestSuccessful(false)
                .responseCode("99")
                .responseMessage("Don't fret this is from our end")
                .responseBody("")
                .build();
        return ResponseEntity.status(500).body(apiResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException exc){
        exc.printStackTrace();
        ApiResponse apiResponse = ApiResponse.builder()
                .requestSuccessful(false)
                .responseCode("99")
                .responseMessage("Don't fret this is from our end")
                .responseBody("")
                .build();
        return ResponseEntity.status(500).body(apiResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleEmptyRequestBodyException(HttpMessageNotReadableException exc) {
        exc.printStackTrace();
        ApiResponse apiResponse = ApiResponse.builder()
                .requestSuccessful(false)
                .responseCode("99")
                .responseMessage("Required Request Payload and none was sent!")
                .responseBody("")
                .build();
        return ResponseEntity.status(400).body(apiResponse);

    }
    

}
