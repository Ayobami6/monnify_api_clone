package com.ayo.monnify_api_clone.auth;

import org.springframework.web.bind.annotation.RestController;

import com.ayo.monnify_api_clone.auth.dto.AuthResponseDto;
import com.ayo.monnify_api_clone.auth.dto.LoginDto;
import com.ayo.monnify_api_clone.auth.dto.RegisterDto;
import com.ayo.monnify_api_clone.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterDto pl) {
        AuthResponseDto authRes = authenticationService.register(pl);
        ApiResponse apiResponse = ApiResponse.builder()
                                    .requestSuccessful(true)
                                    .responseMessage("success")
                                    .responseCode("0")
                                    .responseBody(authRes)
                                    .build();
        return ResponseEntity.status(201).body(apiResponse);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse> logEntity(@RequestBody LoginDto pl) {
        AuthResponseDto authRes = authenticationService.login(pl);
        ApiResponse apiResponse = ApiResponse.builder()
                                    .requestSuccessful(true)
                                    .responseMessage("success")
                                    .responseCode("0")
                                    .responseBody(authRes)
                                    .build();
        
        return ResponseEntity.status(200).body(apiResponse);
    }
    
    

}
