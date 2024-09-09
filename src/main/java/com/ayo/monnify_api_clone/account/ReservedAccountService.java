package com.ayo.monnify_api_clone.account;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.account.dto.CreateReservedAccountDto;
import com.ayo.monnify_api_clone.banks.Bank;
import com.ayo.monnify_api_clone.banks.BankRepository;
import com.ayo.monnify_api_clone.exception.ServiceException;
import com.ayo.monnify_api_clone.utils.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservedAccountService {

    private final ReservedAccountRepository reservedAccountRepository;
    private final AccountMapper accountMapper;
    private final BankRepository bankRepository;
    private final AccountRepository accountRepository;


    public ReservedAccount createReservedAccount(CreateReservedAccountDto pl) {
        // create reserved account instance
        ReservedAccount reservedAccount = accountMapper.toReservedAccount(pl);
        reservedAccountRepository.save(reservedAccount);
        List<Bank> banks = null;
        if (pl.isGetAllAvailableBanks()) {
            // get all available banks and get the top three
            List<Bank> queryBanks = bankRepository.findAll();
            // slice just three
            banks = queryBanks.subList(0, 3);
        } else {
            List<String> preferredBanks = pl.getPreferredBanks();
            banks = preferredBanks.stream().map(item -> bankRepository.findByCode(item).orElseThrow(() -> new ServiceException(404, "Bank not available"))).collect(Collectors.toList());
        }
        // create new accounts with bank info
        banks.forEach(bank -> createBankAccount(bank, reservedAccount));
        // get the create reserved account
        ReservedAccount createdReservedAccount = reservedAccountRepository.findByAccountReference(pl.getAccountReference()).orElseThrow(() -> new ServiceException(406, "Couldn't Create Account"));
        
        return createdReservedAccount;
    }

    // create bank info
    private void createBankAccount(Bank bank, ReservedAccount reservedAccount) {
        String bankAccountNumber = Long.toString(Utils.generateRandomNumber());
        Account bankAccount = Account.builder()
                                        .accountName(reservedAccount.getAccountName())
                                        .reservedAccount(reservedAccount)
                                        .accountName(reservedAccount.getAccountName())
                                        .accountNumber(bankAccountNumber)
                                        .bankCode(bank.getCode())
                                        .bankName(bank.getName())
                                        .build();

        accountRepository.save(bankAccount);
    }

    

}
