package com.ayo.monnify_api_clone.subaccount;

import java.time.LocalDateTime;
import java.util.List;

import com.ayo.monnify_api_clone.utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name = "sub_accounts")
public class SubAccount {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String subAccountCode;

    private String accountName;

    private String accountNumber;

    private String bankCode;

    private String email;

    private String bankName;

    @Builder.Default
    private String currencyCode = "NGN";

    @Column(columnDefinition = "double precision")
    private Double defaultSplitPercentage;

    @JsonIgnore
    private LocalDateTime createAt;
    @JsonIgnore
    private LocalDateTime updatedAt;

    private String settlementProfileCode;

    private List<String> settlementReportEmails;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.subAccountCode = Utils.generateSubAccountCode();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
