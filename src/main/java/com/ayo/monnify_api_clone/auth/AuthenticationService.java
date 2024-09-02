package com.ayo.monnify_api_clone.auth;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.amqp.RabbitMqService;
import com.ayo.monnify_api_clone.auth.dto.AuthResponseDto;
import com.ayo.monnify_api_clone.auth.dto.LoginDto;
import com.ayo.monnify_api_clone.auth.dto.OTPResponseDTO;
import com.ayo.monnify_api_clone.auth.dto.OTPVerifyDTO;
import com.ayo.monnify_api_clone.auth.dto.RegisterDto;
import com.ayo.monnify_api_clone.exception.InternalServerException;
import com.ayo.monnify_api_clone.exception.ServiceException;
import com.ayo.monnify_api_clone.user.UserEntity;
import com.ayo.monnify_api_clone.user.UserRepository;
import com.ayo.monnify_api_clone.utils.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    // Authservice dependencies injection
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RabbitMqService rabbitMqService;

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
            // gernarate otp
            String otp = Utils.generateRandomOTP();
            // cache the otp with the user email as key
            redisTemplate.opsForValue().set(user.getEmail(), otp, 15, TimeUnit.MINUTES);
            // create a new map payload  to be sent to rabbit queue
            Map<String, String> payload = new HashMap<>();
            payload.put("email", user.getEmail());
            payload.put("otp", otp);
            // send payload to rabbit queue
            rabbitMqService.sendMessageToMailQueue(payload);
            // generate token
            token = jwtService.generateToken(user);
            
            
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException(400, "Email already exists");
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

    // verify otp 
    public String verifyOTP(OTPVerifyDTO pl) {
            String otp = (String) redisTemplate.opsForValue().get(pl.getEmail());
            if (otp == null) {
                throw new ServiceException(400, "OTP not found");
            }
            // validate the otp
            if (pl.getOtp().equals(otp)) {
                throw new ServiceException(400, "Invalid OTP");
            }
            // update user's verification status
            UserEntity user = userRepository.findByEmail(pl.getEmail()).orElseThrow(() -> new AuthenticationException("User with the Email not found"));
            user.setVerified(true);
            userRepository.save(user);
            // remove otp from redis
            redisTemplate.delete(pl.getEmail());
            return "OTP verified successfully";
            
    }


    public OTPResponseDTO resendOtp(String email) {
        // find user by email
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new ServiceException(400, "User not found"));
        // generate new otp
        String otp = Utils.generateRandomOTP();
        // cache the otp with the user email as key
        redisTemplate.opsForValue().set(user.getEmail(), otp, 15, TimeUnit.MINUTES);
        // create a new map payload  to be sent to rabbit queue
        Map<String, String> payload = new HashMap<>();
        payload.put("email", user.getEmail());
        payload.put("otp", otp);
        // send payload to rabbit queue
        rabbitMqService.sendMessageToMailQueue(payload);
        return OTPResponseDTO.builder()
                        .otp(otp)
                        .build();
    }
    
}
