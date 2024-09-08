package com.ayo.monnify_api_clone.transaction.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ayo.monnify_api_clone.transaction.enums.CollectionChannel;
import com.ayo.monnify_api_clone.transaction.enums.Status;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AllTransactionsResponseDto {
    private CustomerDto customerDto;
    private LocalDateTime createdOn;
    private String amount;
    private boolean flagged;
    private String currencyCode;
    private LocalDateTime completedOn;
    private String paymentDescription;
    private Status paymentStatus;
    private String transactionReference;
    private String paymentReference;
    private String merchantCode;
    private String merchantName;
    private boolean completed;
    private List<String> paymentMethods;
    private CollectionChannel collectionChannel;


}
