package com.ayo.monnify_api_clone.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Table(name = "card_details")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CardDetail {
    
    @Id
    @GeneratedValue
    private Long id;
    private String cardType;
    private String last4;
    private String expMonth;
    private String expYear;
    private String bin;
    @Column(name = "bank_name", nullable = true)
    private String bankName;
    @Column(name = "bank_code", nullable = true)
    private String bankCode;
    @Builder.Default
    private boolean reusable = false;
    @Column(name = "country_code", nullable = true)
    private String countryCode;
    @Column(name = "card_token", nullable = true)
    private String cardToken;
    @Column(name = "support_tokenization", nullable = true)
    private String supportTokenization;
    private String maskedPan;

}
