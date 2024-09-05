package com.ayo.monnify_api_clone.transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayo.monnify_api_clone.transaction.dto.InitTransactionResponseDto;
import com.ayo.monnify_api_clone.transaction.dto.InitializeTransactionDto;
import com.ayo.monnify_api_clone.utils.ApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/merchants")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;


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

}
