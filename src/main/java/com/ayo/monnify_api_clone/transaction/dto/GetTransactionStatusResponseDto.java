package com.ayo.monnify_api_clone.transaction.dto;

import java.util.*;

import com.ayo.monnify_api_clone.account.CardDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class GetTransactionStatusResponseDto {

    private String transactionReference;
    private String paymentReference;
    private String amountPaid;
    private String totalPayable;
    private String paymentStatus;
    private String paymentDescription;
    private String paidOn;
    private String currency;
    private String paymentMethod;
    private CardDetail cardDetails;
    private HashMap<String, String> customer;
    private Object accountDetails;
    private HashMap<String, Object> product;


}
