package com.ayo.monnify_api_clone.auth;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.auth.dto.AuthResponseDto;
import com.ayo.monnify_api_clone.auth.dto.LoginDto;
import com.ayo.monnify_api_clone.auth.dto.RegisterDto;
import com.ayo.monnify_api_clone.exception.InternalServerException;
import com.ayo.monnify_api_clone.exception.ServiceException;
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
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.expiration}")
    private Long jwtExpiry;

    // register service method
    public AuthResponseDto register(RegisterDto pl) {
        String token = "";
        try {
            
            UserEntity user = UserEntity.builder()
                                .email(pl.getEmail())
                                .firstName(pl.getFirstName())
                                .lastName(pl.getLastName())
                                .phoneNumber(pl.getPhoneNumber())
                                .password(passwordEncoder.encode(pl.getPassword()))
                                .build();
            // save to db
            userRepository.save(user);
    
            // TODO: implement generate otp and sent to email for verification
            // generate token
            token = jwtService.generateToken(user);
            
            
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException(400, "Email already exists");
        } catch (Exception e) {
            throw new InternalServerException();

        }
        return AuthResponseDto.builder()
                            .accessToken(token)
                            .expiresIn(jwtExpiry)
                            .build();
        
    }


    public AuthResponseDto login(LoginDto pl) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                pl.getEmail(),
                pl.getPassword()
            )
        );
        UserEntity user  = userRepository.findByEmail(pl.getEmail()).orElseThrow(() -> new AuthenticationException("User not found"));
        // generate token
        String token = jwtService.generateToken(user);
        return AuthResponseDto.builder()
                        .accessToken(token)
                        .expiresIn(jwtExpiry)
                        .build();
    }

    public AuthResponseDto loginAPI(String token) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(token);
        String decodedString = new String(decodedBytes);
        // split the string
        String[] parts = decodedString.split(":");
        String apiKey = parts[0];
        UserEntity user = userRepository.findByApiKey(apiKey).orElseThrow(() -> new AuthenticationException("User not found"));
        
        String newToken = jwtService.generateTokenAPI(user);
            return AuthResponseDto.builder()
                .accessToken(newToken)
                .expiresIn(jwtExpiry)
                .build();
        }
        



}
