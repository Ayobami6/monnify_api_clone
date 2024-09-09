package com.ayo.monnify_api_clone.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<UserEntity, Long>{

    Optional<UserEntity>findByEmail(String email);
    Optional<UserEntity>findByApiKey(String apiKey);
    Optional<UserEntity>findByContractCode(Long contractCode);
    boolean existsByContractCode(Long contractCode);
    
}