package com.ayo.monnify_api_clone.common.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.transaction.Transaction;
import com.ayo.monnify_api_clone.transaction.TransactionRepository;
import com.ayo.monnify_api_clone.transaction.enums.Status;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SchedulerService {

    private final TransactionRepository transactionRepository;

   @Scheduled(cron = "0 0 0 * * ?")
   public void scheduleTransactionExpiry() {
        System.out.println("Running Transaction Expiry Scheduler.....");
        // TODO: Implement logic to expire transactions
        LocalDate today = LocalDate.now();
        // get all old transactions
        List<Transaction> oldTransactions = transactionRepository.getOldPendingTransactions(today);
        // open a stream on the transaction
        if (oldTransactions.isEmpty()) {
            System.out.println("Transaction is empty");
            System.out.println(oldTransactions);
        } else {
            System.out.println("Got here!");
            oldTransactions.forEach(this::updateTransaction);
        }
   }

   private Transaction updateTransaction(Transaction transaction) {
    transaction.setPaymentStatus(Status.EXPIRED);
    System.out.println("Updated transaction " + transaction.getId());
    return transactionRepository.save(transaction);
   }

}
