package com.ayo.monnify_api_clone.transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ayo.monnify_api_clone.transaction.enums.Status;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction>findByTransactionReference(String transactionReference);

    @Query(value = "Select * from Transactions t where CAST(t.created_at AS DATE) < :today AND (t.payment_status = 'PENDING')", nativeQuery = true)
    ArrayList<Transaction>getOldPendingTransactions(@Param("today") LocalDate today); 

    @Query(
        value ="SELECT * FROM Transactions t WHERE (:customer_name IS NULL OR t.customer_name = :customer_name) AND (:customer_email IS NULL OR t.customer_email = :customer_email) AND (:amount IS NULL OR t.amount = :amount) AND (:payment_status IS NULL OR t.payment_status = :payment_status) AND (:from_amount IS NULL OR t.amount >= :from_amount) AND (:to_amount IS NULL OR t.amount <= :to_amount) AND (:toDate IS NULL OR CAST(t.created_at AS DATE) <= :toDate  AND (:fromDate IS NULL OR CAST(t.created_at AS DATE) >= :fromDate))"
    , 
    nativeQuery = true)
    Page<Transaction> findByFilter(
        @Param("customer_name") String customerName,
        @Param("customer_email") String customerEmail,
        @Param("amount") String amount,
        @Param("from_amount") String fromAmount,
        @Param("to_amount") String toAmount,
        @Param("payment_status") String paymentStatus,
        @Param("toDate") LocalDate toDate,
        @Param("fromDate") LocalDate fromDate,
        Pageable pageable
    );


}
