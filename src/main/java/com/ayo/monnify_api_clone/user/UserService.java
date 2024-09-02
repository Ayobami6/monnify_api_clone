package com.ayo.monnify_api_clone.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.amqp.RabbitMqService;
import com.ayo.monnify_api_clone.exception.ServiceException;
import com.ayo.monnify_api_clone.user.dto.ApiKeyGetResponseDto;
import com.ayo.monnify_api_clone.utils.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RabbitMqService rabbitMqService;

    public UserEntity getUserByEmail(String email) throws ServiceException  {
        return userRepository.findByEmail(email).orElseThrow(() -> new ServiceException(404, "Could not find user"));
        
    }

    public ApiKeyGetResponseDto getUserApiKeysOTP(String email) {
        // check id the user exists
        UserEntity user = getUserByEmail(email);
        // generate a token
        String otp = Utils.generateRandomOTP();
        // cache the otp
        
        redisTemplate.opsForValue().set(user.getEmail(), otp, 5, TimeUnit.MINUTES);

        Map<String, String> payload = new HashMap<>();

        payload.put("email", user.getEmail());
        payload.put("otp", otp);
        // send payload to rabbit queue
        rabbitMqService.sendMessageToMailQueue(payload);

        return ApiKeyGetResponseDto.builder()
                        .otp(otp)
                        .build();
    }

}
