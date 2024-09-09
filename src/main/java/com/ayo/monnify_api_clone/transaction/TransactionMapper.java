package com.ayo.monnify_api_clone.transaction;

import java.util.*;

import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.account.CardDetail;
import com.ayo.monnify_api_clone.account.OneTimeAccount;
import com.ayo.monnify_api_clone.transaction.dto.AllTransactionsResponseDto;
import com.ayo.monnify_api_clone.transaction.dto.BankTransferInitResponseDto;
import com.ayo.monnify_api_clone.transaction.dto.CustomerDto;
import com.ayo.monnify_api_clone.transaction.dto.GetTransactionStatusResponseDto;
import com.ayo.monnify_api_clone.transaction.enums.Status;

import java.time.format.DateTimeFormatter;


@Service
public class TransactionMapper {
    
    // map to transfer dto
    public BankTransferInitResponseDto toBankTransferInitResponseDto(Map<String, Object> data) {
        return BankTransferInitResponseDto.builder()
                .transactionReference(data.get("transactionReference").toString())
                .paymentReference(data.get("paymentReference").toString())
                .amount(Double.parseDouble(data.get("amount").toString()))
                .totalPayable(Double.parseDouble(data.get("amount").toString()))
                .accountDurationSeconds("2345")
                .accountNumber(data.get("accountNumber").toString())
                .accountName(data.get("accountName").toString())
                .bankName(data.get("bankName").toString())
                .bankCode(data.get("bankCode").toString())
                .collectionChannel(data.get("collectionChannel").toString())
                .expiresOn(data.get("expiresOn"))
                .requestTime(data.get("requestTime"))
                .ussdPayment((data.get("ussdPayment")!= null) ? 
                        data.get("ussdPayment").toString() : null)
                .fee( Integer.parseInt(data.get("fee").toString()) )
                .productInformation(null)
                .build();
    }

    public AllTransactionsResponseDto toTransactionResponseDto(Transaction transaction) {
        CustomerDto customerDto = CustomerDto.builder()
                                    .email(transaction.getCustomerEmail())
                                    .name(transaction.getCustomerName())
                                    .merchantCode(transaction.getContractCode())
                                    .build();
        return AllTransactionsResponseDto.builder()
                    .customerDto(customerDto)
                    .amount(transaction.getAmount())
                    .collectionChannel(transaction.getCollectionChannel())
                    .completed(transaction.getCompleted())
                    .createdOn(transaction.getCreatedAt())
                    .completedOn(transaction.getCompletedOn())
                    .currencyCode(transaction.getCurrencyCode())
                    .flagged(transaction.getFlagged())
                    .merchantCode(transaction.getContractCode())
                    .paymentDescription(transaction.getPaymentDescription())
                    .paymentMethods(transaction.getPaymentMethods())
                    .paymentStatus(transaction.getPaymentStatus())
                    .paymentReference(transaction.getPaymentReference())
                    .transactionReference(transaction.getTransactionReference())
                    .build();
    }

    public GetTransactionStatusResponseDto toTransactionStatusResponseDto(Transaction pl, CardDetail cardDetail, OneTimeAccount oneTimeAccount) {
        HashMap<String, String> customer = new HashMap<String, String>();
        customer.put("email", pl.getCustomerEmail());
        customer.put("name", pl.getCustomerName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
        String formattedDate = pl.getPaymentStatus() == Status.PAID ? pl.getUpdatedAt().format(formatter) : null;
        HashMap<String, Object> product = new HashMap<String, Object>();
        product.put("type", pl.getCollectionChannel());
        product.put("paymentReference", pl.getPaymentReference());
        return GetTransactionStatusResponseDto.builder()
                                                .accountDetails(oneTimeAccount)
                                                .amountPaid(pl.getAmount())
                                                .cardDetails(cardDetail)
                                                .customer(customer)
                                                .currency("NGN")
                                                .paidOn(formattedDate)
                                                .paymentDescription(pl.getPaymentDescription())
                                                .paymentMethod(pl.getPaymentMethod().toString())
                                                .transactionReference(pl.getTransactionReference())
                                                .paymentStatus(pl.getPaymentStatus().toString())
                                                .paymentReference(pl.getPaymentReference())
                                                .totalPayable(pl.getAmount())
                                                .product(product)
                                                .build();




    }






}
