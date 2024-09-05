package com.ayo.monnify_api_clone.banks;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayo.monnify_api_clone.banks.dto.BankDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/banks")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @GetMapping("")
    public List<BankDto> getAllBanks() {
        return bankService.getAllBanks();
    }

}
