package com.ayo.monnify_api_clone.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayo.monnify_api_clone.account.dto.CreateReservedAccountDto;
import com.ayo.monnify_api_clone.utils.ApiResponse;
import com.ayo.monnify_api_clone.utils.ApiResponseMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant")
public class ReservedAccountController {

    private final ReservedAccountService reservedAccountService;
    private final ApiResponseMapper apiResponseMapper;

    @PostMapping("/bank-transfer/reserved-account")
    public ResponseEntity<ApiResponse> reserveAccount(@RequestBody CreateReservedAccountDto pl) {
        ReservedAccount reservedAccount = reservedAccountService.createReservedAccount(pl);
        ApiResponse response = apiResponseMapper.mapDataToResponse(reservedAccount);
        return ResponseEntity.status(200).body(response);      
    }

}
