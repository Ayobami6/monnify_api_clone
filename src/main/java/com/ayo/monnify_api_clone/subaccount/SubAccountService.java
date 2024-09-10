package com.ayo.monnify_api_clone.subaccount;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.account.Account;
import com.ayo.monnify_api_clone.account.AccountRepository;
import com.ayo.monnify_api_clone.banks.Bank;
import com.ayo.monnify_api_clone.banks.BankRepository;
import com.ayo.monnify_api_clone.exception.ServiceException;
import com.ayo.monnify_api_clone.subaccount.dto.CreateSubAccountDto;
import com.ayo.monnify_api_clone.utils.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubAccountService {

    private final SubAccountRepository subAccountRepository;
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;

    public List<SubAccount> createSubAccount(List<CreateSubAccountDto> pl) {
        List<SubAccount> subAccounts = new ArrayList<>();
        for (CreateSubAccountDto createSubAccountDto : pl) {
            subAccounts.add(createAccount(createSubAccountDto));
        }
        return subAccounts;
    }


    private SubAccount createAccount(CreateSubAccountDto pl) {
        // get the bank info from bank table
        Bank bank = bankRepository.findByCode(pl.getBankCode()).orElseThrow(() -> new ServiceException(404, "Bank not found"));
        Account account = accountRepository.findByAccountNumber(pl.getAccountNumber()).orElseThrow(() -> new ServiceException(404, "Account not found"));
        String profileCode = Utils.generateRandomNumber().toString();
        SubAccount subAccount = SubAccount.builder()
                                        .accountName(account.getAccountName())
                                        .accountNumber(account.getAccountNumber())
                                        .bankCode(bank.getCode())
                                        .bankName(bank.getName())
                                        .email(pl.getEmail())
                                        .defaultSplitPercentage(pl.getDefaultSplitPercentage())
                                        .settlementProfileCode(profileCode)
                                        .settlementReportEmails(new ArrayList<String>())
                                        .build();
        return subAccountRepository.save(subAccount);


    };

    public void deleteSubAccount(String SubAccountCode) {
        // get the subAccount
        SubAccount subAccount = subAccountRepository.findBySubAccountCode(SubAccountCode).orElseThrow(() -> new ServiceException(404, "SubAccount not found"));
        // delete the subAccount from the database
        subAccountRepository.delete(subAccount);
    }

    public SubAccount updateSubAccount(String SubAccountCode, CreateSubAccountDto pl) {
        // get the subAccount
        SubAccount subAccount = subAccountRepository.findBySubAccountCode(SubAccountCode).orElseThrow(() -> new ServiceException(404, "SubAccount not found"));
        // update the subAccount with new details
        Bank bank = bankRepository.findByCode(pl.getBankCode()).orElseThrow(() -> new ServiceException(404, "Bank not found"));
        Account account = accountRepository.findByAccountNumber(pl.getAccountNumber()).orElseThrow(() -> new ServiceException(404, "Account not found"));
        subAccount.setAccountName(account.getAccountName());
        subAccount.setEmail(pl.getEmail());
        subAccount.setDefaultSplitPercentage(pl.getDefaultSplitPercentage());
        subAccount.setBankCode(bank.getCode());
        subAccount.setBankName(bank.getName());
        
        // save the updated subAccount
        return subAccountRepository.save(subAccount);
    }

    public List<SubAccount>getAllSubAccounts(String email) {
        // get all subAccounts associated with the given email
        return subAccountRepository.findAllByEmail(email);
    }



}
