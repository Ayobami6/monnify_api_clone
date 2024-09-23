package com.ayo.monnify_api_clone.invoice.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceDto {

    private Integer amount;
    private String invoiceReference;
    private String description;
    private String contractCode;
    private String customerEmail;
    private String customerName;
    private LocalDate expiryDate;
    private String redirectUrl;
    private List<IncomeSplitConfigDto> incomeSplitConfig;

}
