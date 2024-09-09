package com.ayo.monnify_api_clone.account;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    private Long id;

    private String accountNumber;
    private String accountName;
    private String bankName;
    private String bankCode;

    @ManyToOne()
    @JoinColumn(
        name = "account_reference",
        referencedColumnName = "account_reference"
    )
    @JsonManagedReference
    private ReservedAccount reservedAccount;

}
