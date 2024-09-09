package com.ayo.monnify_api_clone.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservedAccountRepository extends JpaRepository<ReservedAccount, Long> {  
    Optional<ReservedAccount>findByAccountReference(String accountReference);
} 