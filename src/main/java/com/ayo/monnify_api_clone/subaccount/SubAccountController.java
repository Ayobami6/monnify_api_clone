package com.ayo.monnify_api_clone.subaccount;

import org.springframework.web.bind.annotation.RestController;

import com.ayo.monnify_api_clone.auth.AuthenticationException;
import com.ayo.monnify_api_clone.subaccount.dto.CreateSubAccountDto;
import com.ayo.monnify_api_clone.utils.ApiResponse;
import com.ayo.monnify_api_clone.utils.ApiResponseMapper;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant/sub-accounts")
public class SubAccountController {

    private final SubAccountService subAccountService;
    private final ApiResponseMapper apiResponseMapper;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createSubAccount(@RequestBody List<CreateSubAccountDto> pl) {
        List<SubAccount> subAccounts = subAccountService.createSubAccount(pl);
        ApiResponse response = apiResponseMapper.mapDataToResponse(subAccounts);
        return ResponseEntity.status(201).body(response);

    }

    @DeleteMapping("/{subAccountCode}")
    public ResponseEntity<ApiResponse> deleteAllSubAccounts(@PathVariable("subAccountCode") String subAccountCode) {
        subAccountService.deleteSubAccount(subAccountCode);
        ApiResponse response = apiResponseMapper.mapDataToResponse(null);
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/{subAccountCode}")
    public ResponseEntity<ApiResponse> updateSubAccount(@PathVariable String subAccountCode, @RequestBody CreateSubAccountDto pl) {
        SubAccount updatedSubAccount = subAccountService.updateSubAccount(subAccountCode, pl);
        ApiResponse response = apiResponseMapper.mapDataToResponse(updatedSubAccount);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllSubAccounts() {
        // get Authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = new String();

        if (authentication != null && authentication.isAuthenticated()) {
            // Retrieve the UserDetails object
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            userEmail = userDetails.getUsername();

        } else {
            throw new AuthenticationException("Nice try, can't bypass the authentication system");
        }
        List<SubAccount> subAccounts = subAccountService.getAllSubAccounts(userEmail);
        ApiResponse response = apiResponseMapper.mapDataToResponse(subAccounts);
        return ResponseEntity.status(200).body(response);
    }
    

}
