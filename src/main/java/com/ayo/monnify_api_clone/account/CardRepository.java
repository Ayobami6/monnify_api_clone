package com.ayo.monnify_api_clone.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardDetail, Long> {
  
} 
