package com.ayo.monnify_api_clone.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.auth.dto.AuthResponseDto;
import com.ayo.monnify_api_clone.auth.dto.LoginDto;
import com.ayo.monnify_api_clone.auth.dto.RegisterDto;
import com.ayo.monnify_api_clone.user.UserEntity;
import com.ayo.monnify_api_clone.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    // Authservice dependencies injection
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.expiration}")
    private Long jwtExpiry;

    // register service method
    public AuthResponseDto register(RegisterDto pl) {
        UserEntity user = UserEntity.builder()
                            .email(pl.getEmail())
                            .firstName(pl.getFirstName())
                            .lastName(pl.getLastName())
                            .phoneNumber(pl.getPhoneNumber())
                            .build();
        // save to db
        userRepository.save(user);

        // TODO: implement generate otp and sent to email for verification
        // generate token
        String token = jwtService.generateToken(user);
        return AuthResponseDto.builder()
                        .accessToken(token)
                        .expiresIn(jwtExpiry)
                        .build();
    }


    public AuthResponseDto login(LoginDto pl) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                pl.getApiKey(),
                pl.getSecretKey()
            )
        );
        UserEntity user  = userRepository.findByApiKey(pl.getApiKey()).orElseThrow(() -> new RuntimeException("User not found"));
        // generate token
        String token = jwtService.generateToken(user);
        return AuthResponseDto.builder()
                        .accessToken(token)
                        .expiresIn(jwtExpiry)
                        .build();
    }



}
