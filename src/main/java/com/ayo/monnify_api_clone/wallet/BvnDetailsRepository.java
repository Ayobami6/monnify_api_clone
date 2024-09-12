package com.ayo.monnify_api_clone.wallet;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BvnDetailsRepository extends JpaRepository<BvnDetails, Long> {

    Optional<BvnDetails>findByCustomerEmail(String customerEmail);

}
