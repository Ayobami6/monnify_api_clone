package com.ayo.monnify_api_clone.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayo.monnify_api_clone.account.dto.CreateReservedAccountDto;
import com.ayo.monnify_api_clone.account.dto.CreateReservedAccountInvoiceDto;
import com.ayo.monnify_api_clone.account.dto.InvoiceResponseDto;
import com.ayo.monnify_api_clone.utils.ApiResponse;
import com.ayo.monnify_api_clone.utils.ApiResponseMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant")
public class ReservedAccountController {

    private final ReservedAccountService reservedAccountService;
    private final ApiResponseMapper apiResponseMapper;

    @PostMapping("/bank-transfer/reserved-accounts")
    public ResponseEntity<ApiResponse> reserveAccount(@RequestBody CreateReservedAccountDto pl) {
        ReservedAccount reservedAccount = reservedAccountService.createReservedAccount(pl);
        ApiResponse response = apiResponseMapper.mapDataToResponse(reservedAccount);
        return ResponseEntity.status(200).body(response);      
    }
    @GetMapping("/bank-transfer/reserved-accounts/{accountReference}")
    public ResponseEntity<ApiResponse> getReservedAccount(@PathVariable String accountReference) {
        ReservedAccount reservedAccount = reservedAccountService.getReservedAccountByAccountRef(accountReference);
        ApiResponse response = apiResponseMapper.mapDataToResponse(reservedAccount);
        return ResponseEntity.status(200).body(response);      
    }

    @PostMapping("/bank-transfer/reserved-account-invoice")
    public ResponseEntity<ApiResponse> createInvoice(@RequestBody CreateReservedAccountInvoiceDto pl) {
        InvoiceResponseDto reservedAccountInvoice = reservedAccountService.createReservedAccountInvoice(pl);
        ApiResponse response = apiResponseMapper.mapDataToResponse(reservedAccountInvoice);
        return ResponseEntity.status(200).body(response);      
    }

    @DeleteMapping("/bank-transfer/reserved-accounts/reference/{accountReference}")
    public ResponseEntity<ApiResponse> deleteReservedAccount(@PathVariable String accountReference) {
        ReservedAccount reservedAccount = reservedAccountService.deAllocateReservedAccount(accountReference);
        ApiResponse response = apiResponseMapper.mapDataToResponse(reservedAccount);
        return ResponseEntity.status(200).body(response);
    }

}
