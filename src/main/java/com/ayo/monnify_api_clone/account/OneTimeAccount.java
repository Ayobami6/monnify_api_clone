package com.ayo.monnify_api_clone.account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "generated_accounts")
public class OneTimeAccount {

    @Id
    @GeneratedValue
    private Long id;

    private String accountName;
    private String accountNumber;
    private String bankName;
    private String bankCode;

}
