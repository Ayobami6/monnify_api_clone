package com.ayo.monnify_api_clone.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account>findByAccountNumber(String accountNumber);
}
