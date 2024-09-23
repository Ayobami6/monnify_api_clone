package com.ayo.monnify_api_clone.invoice;

import java.time.LocalDate;
import java.util.List;

import com.ayo.monnify_api_clone.invoice.enums.InvoiceStatus;
import com.ayo.monnify_api_clone.utils.Utils;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

    private String transactionReference;

    private String accountName;
    private String accountNumber;
    private String bankCode;
    private String bankName;
    private String createdBy;

    private String checkoutUrl;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    private LocalDate createdOn;

    private LocalDate updatedDate;

    @OneToMany(
        mappedBy = "invoice",
        cascade = CascadeType.ALL

    )
    @JsonManagedReference
    private List<IncomeSplitConfig> incomeSplitConfig;


    @PrePersist
    void prePersist() {
        this.transactionReference = Utils.generateTransactionRef();
        this.createdOn = LocalDate.now();
    }

    @PreUpdate
    void preUpdate() {
        this.updatedDate = LocalDate.now();

    }

}
