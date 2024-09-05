package com.ayo.monnify_api_clone.banks;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long>{

    Optional<Bank>findByCode(String code);

}