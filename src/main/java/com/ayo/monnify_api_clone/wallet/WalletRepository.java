package com.ayo.monnify_api_clone.wallet;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Page<Wallet>findAllByContractCode(String contractCode, Pageable pageable);

}
