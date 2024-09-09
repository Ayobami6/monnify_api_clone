package com.ayo.monnify_api_clone.account;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.mapping.Any;

import com.ayo.monnify_api_clone.account.enums.ReservedAccountStatus;
import com.ayo.monnify_api_clone.account.enums.ReservedAccountType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reserved_accounts")
public class ReservedAccount {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "account_reference", nullable = false, unique = true)
    private String accountReference;
    private String accountName;
    @Builder.Default
    private String currencyCode = "NGN";
    private String contractCode;
    private String customerEmail;
    private String customerName;
    private String bvn;
    private String nin;
    private boolean getAllAvailableBanks;
    private List<String> preferredBanks;
    private Any incomeSplitConfig;
    private boolean restrictPaymentSource;
    private Any allowedPaymentSource;
    private ReservedAccountType reservedAccountType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Builder.Default
    private ReservedAccountStatus status = ReservedAccountStatus.ACTIVE;
    @OneToMany(
        mappedBy = "reservedAccount"
    )
    @JsonBackReference
    private List<Account> accounts;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
