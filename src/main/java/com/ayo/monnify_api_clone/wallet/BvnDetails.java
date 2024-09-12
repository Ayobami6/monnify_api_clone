package com.ayo.monnify_api_clone.wallet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bvn_details")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BvnDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    @JsonIgnore
    @Column(nullable = true, name = "customer_email")
    private String customerEmail;
    private String bvn;
    private String bvnDateOfBirth;

}
