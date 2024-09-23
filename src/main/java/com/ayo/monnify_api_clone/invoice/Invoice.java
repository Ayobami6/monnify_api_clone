package com.ayo.monnify_api_clone.invoice;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "invoice")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue
    private Long id;

    private String amount;

    @Column(name = "invoice_reference", unique = true)
    private String invoiceReference;

    private String customerEmail;

    private String customerName;

    private String contractCode;

    private String description;

    private LocalDate expiryDate;

    private String redirectUrl;

    @OneToMany(
        mappedBy = "invoice",
        cascade = CascadeType.ALL

    )
    @JsonManagedReference
    private List<IncomeSplitConfig> incomeSplitConfig;



}
