package com.ayo.monnify_api_clone.banks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.banks.dto.BankDto;
import com.ayo.monnify_api_clone.banks.mapper.BankMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;


    public List<BankDto> getAllBanks() {
        return bankRepository.findAll().stream().map(bankMapper::toBankDto).collect(Collectors.toList());       
    }
}
