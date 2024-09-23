package com.ayo.monnify_api_clone.invoice.dto;

import java.time.LocalDate;
import java.util.List;

import com.ayo.monnify_api_clone.invoice.IncomeSplitConfig;
import com.ayo.monnify_api_clone.invoice.enums.InvoiceStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponseDto {

    private Integer amount;
    private String createdBy;
    private LocalDate createdOn;
    private String checkoutUrl;
    private String redirectUrl;
    private String customerEmail;
    private String customerName;
    private String accountName;
    private String accountNumber;
    private String bankCode;
    private String bankName;
    private InvoiceStatus invoiceStatus;
    private LocalDate expiryDate;
    private String transactionReference;
    private List<IncomeSplitConfig> incomeSplitConfig;

}
