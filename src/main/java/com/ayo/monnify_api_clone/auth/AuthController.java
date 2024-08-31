package com.ayo.monnify_api_clone.auth;

import org.springframework.web.bind.annotation.RestController;

import com.ayo.monnify_api_clone.amqp.RabbitMqService;
import com.ayo.monnify_api_clone.auth.dto.AuthResponseDto;
import com.ayo.monnify_api_clone.auth.dto.LoginDto;
import com.ayo.monnify_api_clone.auth.dto.RegisterDto;
import com.ayo.monnify_api_clone.utils.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor

public class AuthController {

    private final AuthenticationService authenticationService;
    private final RabbitMqService rabbitMqService;

    @PostMapping("/auth/register")
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


    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse>login(@RequestBody LoginDto pl) {
        AuthResponseDto authRes = authenticationService.login(pl);
        ApiResponse apiResponse = ApiResponse.builder()
                                    .requestSuccessful(true)
                                    .responseMessage("success")
                                    .responseCode("0")
                                    .responseBody(authRes)
                                    .build();
        
        return ResponseEntity.status(200).body(apiResponse);
    }

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<ApiResponse> loginApi(HttpServletRequest request) {
        //get auth header from request
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            throw new AuthenticationException("Unauthorized!");
        }
        if (!authHeader.startsWith("Basic ")) {
            throw new AuthenticationException("Unauthorized!");
        }
        String token = authHeader.substring(6);
        AuthResponseDto authResponseDto = authenticationService.loginAPI(token);

        ApiResponse apiResponse = ApiResponse.builder()
                                    .requestSuccessful(true)
                                    .responseMessage("success")
                                    .responseCode("0")
                                    .responseBody(authResponseDto)
                                    .build();
        return ResponseEntity.status(200).body(apiResponse);
    }
    

    @GetMapping("/test")
    public String testRabbit(@RequestParam String message) {
        rabbitMqService.sendMessageToMailQueue(message);
        return "Message sent to RabbitMQ successfully";
        
    }
    
    

}
