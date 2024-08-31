package com.ayo.monnify_api_clone.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity getUserByEmail(String email) throws ServiceException  {
        return userRepository.findByEmail(email).orElseThrow(() -> new ServiceException(404, "Could not find user"));
        
    }

}
