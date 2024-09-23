package com.ayo.monnify_api_clone.invoice;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "income_split")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeSplitConfig {


    @Id
    @GeneratedValue
    private Long id;

    private String invoiceReference;

    private String subAccountCode;

    @ManyToOne(
        cascade = CascadeType.ALL
    )
    @JoinColumn(
        name = "invoice_id",
        referencedColumnName = "invoice_reference"
    )
    @JsonBackReference
    private Invoice invoice;

    private Integer splitAmount;

    private Double feePercentage;

    private Boolean feeBearer;


}
