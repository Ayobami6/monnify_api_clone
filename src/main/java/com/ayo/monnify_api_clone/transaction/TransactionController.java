package com.ayo.monnify_api_clone.transaction;

import com.ayo.monnify_api_clone.transaction.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayo.monnify_api_clone.utils.ApiResponse;
import com.ayo.monnify_api_clone.utils.ApiResponseMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final ApiResponseMapper apiResponseMapper;


    @PostMapping("transaction/init-transaction")
    public ResponseEntity<ApiResponse> initTransaction(@RequestBody InitializeTransactionDto pl) {
        InitTransactionResponseDto res =  transactionService.initTransaction(pl);

        ApiResponse response = ApiResponse.builder()
                                    .requestSuccessful(true)
                                    .responseMessage("success")
                                    .responseCode("0")
                                    .responseBody(res)
                                    .build();

        return ResponseEntity.status(200).body(response);

    }

    @PostMapping("/bank-transfer/init-payment")
    public ResponseEntity<ApiResponse>bankTransfer(@RequestBody BankTransferDto pl) {
        BankTransferInitResponseDto res = transactionService.initializeBankTransfer(pl);

        ApiResponse response = apiResponseMapper.mapDataToResponse(res);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/cards/charge")
    public ResponseEntity<ApiResponse>cardCharge(@RequestBody ChargeCardDto pl) {
        CardChargeResponseDto res = transactionService.chargeCard(pl);

        ApiResponse response = apiResponseMapper.mapDataToResponse(res);
        return ResponseEntity.status(200).body(response);
    }
    

}
