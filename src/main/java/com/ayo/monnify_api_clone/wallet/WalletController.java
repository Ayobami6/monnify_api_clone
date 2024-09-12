package com.ayo.monnify_api_clone.wallet;

import org.springframework.web.bind.annotation.RestController;

import com.ayo.monnify_api_clone.utils.ApiResponse;
import com.ayo.monnify_api_clone.utils.ApiResponseMapper;
import com.ayo.monnify_api_clone.wallet.dto.CreateWalletDto;
import com.ayo.monnify_api_clone.wallet.dto.WalletResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant/disbursements")
public class WalletController {

    private final WalletService walletService;
    private final ApiResponseMapper apiResponseMapper;


    @PostMapping("/wallet")
    public ResponseEntity<ApiResponse>createWallet(@Valid @RequestBody CreateWalletDto pl) {
        WalletResponseDto walletResponseDto = walletService.createWallet(pl);
        ApiResponse response = apiResponseMapper.mapDataToResponse(walletResponseDto);

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/wallet")
    public ResponseEntity<ApiResponse> getWallets(
        @RequestParam(defaultValue = "0") int pageNo,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        PageRequest pageable = PageRequest.of(pageNo, pageSize);
        Page<WalletResponseDto> wallets = walletService.getAllMerchantWallet(pageable);
        ApiResponse response = apiResponseMapper.mapDataToResponse(wallets);
        return ResponseEntity.status(200).body(response);
    }
    
    

}
