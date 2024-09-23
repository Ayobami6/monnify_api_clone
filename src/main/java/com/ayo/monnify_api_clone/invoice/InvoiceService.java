package com.ayo.monnify_api_clone.invoice;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    // inject repository
    private final InvoiceRepository invoiceRepository;

}
