package com.ayo.monnify_api_clone.transaction;

import com.ayo.monnify_api_clone.transaction.dto.*;
import com.ayo.monnify_api_clone.transaction.enums.Status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayo.monnify_api_clone.utils.ApiResponse;
import com.ayo.monnify_api_clone.utils.ApiResponseMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


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

    @GetMapping("/transactions/search")
    public ResponseEntity<ApiResponse> getTransactions(
        @RequestParam(required = false) String customerName,
        @RequestParam(required = false) String customerEmail,
        @RequestParam(required = false) String amount,
        @RequestParam(required = false) String fromAmount,
        @RequestParam(required = false) String toAmount,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String to,
        @RequestParam(required = false) String from,
        @RequestParam(required = false) String paymentStatus,
        @RequestParam(required = false) String[] sortBy
    ) {
        LocalDate toDate = (to != null) ? LocalDate.parse(to, DateTimeFormatter.ISO_DATE_TIME) : null;
        LocalDate fromDate = (from!= null)? LocalDate.parse(from, DateTimeFormatter.ISO_DATE_TIME) : null;
        
        
        Sort sort = sortBy != null ? Sort.by(sortBy) : null;
        
        Pageable pageable = sort != null ? PageRequest.of(page, size, sort) : PageRequest.of(page, size);

        GetAllTransactionsQueryParams queryParams = GetAllTransactionsQueryParams.builder()
                                                                .pageable(pageable)
                                                                .amount(amount)
                                                                .customerEmail(customerEmail)
                                                                .customerName(customerName)
                                                                .fromAmount(fromAmount)
                                                                .toAmount(toAmount)
                                                                .from(fromDate)
                                                                .to(toDate)
                                                                .paymentStatus(paymentStatus)
                                                                .build();

        PageImpl<AllTransactionsResponseDto> res = transactionService.getTransactions(queryParams);

        ApiResponse response = apiResponseMapper.mapDataToResponse(res);

        return ResponseEntity.status(200).body(response);
    }
    
}
