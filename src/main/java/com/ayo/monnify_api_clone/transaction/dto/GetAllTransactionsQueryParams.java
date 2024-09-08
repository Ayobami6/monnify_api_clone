package com.ayo.monnify_api_clone.transaction.dto;

import org.springframework.data.domain.Pageable;

import com.ayo.monnify_api_clone.transaction.enums.Status;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Builder
public class GetAllTransactionsQueryParams {
    private Pageable pageable;
    private String fromAmount;
    private String toAmount;
    private String amount;
    private String paymentStatus;
    private String customerName;
    private String customerEmail;
    private LocalDateTime from;
    private LocalDateTime to;

}
