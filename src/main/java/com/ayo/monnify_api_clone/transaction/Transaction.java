package com.ayo.monnify_api_clone.transaction;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue
    private Long Id;

    @Column(name = "contract_code", nullable = false, updatable = false)
    private Long contractCode;

    private String transactionReference;

    private String amount;

    private String customerName;
    private String customerEmail;
    private String paymentReference;
    private String paymentDescription;
    private String currencyCode;
    private String redirectUrl;
    private List<String> paymentMethods;

    @Builder.Default
    private Boolean flagged = false;

    private String merchantName;


    private String apiKey;

    private LocalDateTime completedOn;

    @Builder.Default
    private Boolean completed = false;


    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", columnDefinition = "Varchar(255)")
    private Status paymentStatus;


    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", columnDefinition = "Varchar(255)")
    private PaymentMethod paymentMethod = PaymentMethod.CARD;



    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
