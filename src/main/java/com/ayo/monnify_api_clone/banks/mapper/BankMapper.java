package com.ayo.monnify_api_clone.banks.mapper;

import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.banks.Bank;
import com.ayo.monnify_api_clone.banks.dto.BankDto;


/*
 * Maps bank entity to corresponding Dtos
 */
@Service
public class BankMapper {

    public BankDto toBankDto(Bank bank) {
        return BankDto.builder()
                .name(bank.getName())
                .baseUssdCode(bank.getBaseUssdCode())
                .ussdTemplate(bank.getUssdTemplate())
                .code(bank.getCode())
                .build();
    }

}
