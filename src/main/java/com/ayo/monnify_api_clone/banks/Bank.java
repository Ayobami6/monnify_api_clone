package com.ayo.monnify_api_clone.banks;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "banks")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Bank {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String code;

    private String ussdTemplate;

    private String baseUssdCode;


}
