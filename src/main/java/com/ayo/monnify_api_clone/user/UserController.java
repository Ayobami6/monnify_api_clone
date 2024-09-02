package com.ayo.monnify_api_clone.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayo.monnify_api_clone.user.dto.ApiKeyGetResponseDto;
import com.ayo.monnify_api_clone.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/merchants")
public class UserController {

    private final UserService userService;


    @GetMapping("api-keys")
    public ResponseEntity<ApiResponse> getApiKeys(@RequestBody String param) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Retrieve the UserDetails object
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String userEmail = userDetails.getUsername();

            ApiKeyGetResponseDto res = userService.getUserApiKeysOTP(userEmail);
            ApiResponse response = ApiResponse.builder()
                                            .requestSuccessful(true)
                                            .responseCode("0")
                                            .responseMessage("success")
                                            .responseBody(res)
                                            .build();
            return ResponseEntity.status(200).body(response);
        }
        return ResponseEntity.status(401).body(ApiResponse.builder()
                        .requestSuccessful(false)
                        .responseCode("99")
                        .responseMessage("Unauthorized")
                        .responseBody(null)
                        .build());
    }
    

}
