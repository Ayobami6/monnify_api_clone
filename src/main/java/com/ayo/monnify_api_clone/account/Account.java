package com.ayo.monnify_api_clone.account;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String accountNumber;
    private String accountName;
    private String bankName;
    private String bankCode;

    @ManyToOne(
        cascade = CascadeType.ALL
    )
    @JoinColumn(
        name = "reserved_account_id",
        referencedColumnName = "account_reference"
    )
    @JsonBackReference
    private ReservedAccount reservedAccount;

}
