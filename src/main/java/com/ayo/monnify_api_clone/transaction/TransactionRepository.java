package com.ayo.monnify_api_clone.transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction>findByTransactionReference(String transactionReference);

    @Query(value = "Select * from Transactions t where CAST(t.created_at AS DATE) < :today AND (t.payment_status = 'PENDING')", nativeQuery = true)
    ArrayList<Transaction>getOldPendingTransactions(@Param("today") LocalDate today); 

}
