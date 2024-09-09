package com.ayo.monnify_api_clone.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardDetail, Long> {
    
    Optional<CardDetail>findByTransactionReference(String transactionReference);
  
} 
