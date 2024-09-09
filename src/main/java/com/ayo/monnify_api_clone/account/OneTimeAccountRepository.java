package com.ayo.monnify_api_clone.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OneTimeAccountRepository extends JpaRepository<OneTimeAccount, Long> {
    Optional<OneTimeAccount>findByPaymentReference(String paymentReference);

}
