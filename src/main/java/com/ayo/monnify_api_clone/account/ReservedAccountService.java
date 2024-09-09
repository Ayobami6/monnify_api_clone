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
import com.ayo.monnify_api_clone.user.UserRepository;
import com.ayo.monnify_api_clone.utils.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservedAccountService {

    private final ReservedAccountRepository reservedAccountRepository;
    private final AccountMapper accountMapper;
    private final BankRepository bankRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;


    public ReservedAccount createReservedAccount(CreateReservedAccountDto pl) {
        // validate merchant contract Code
        if (!userRepository.existsByContractCode(Long.parseLong(pl.getContractCode()))){
            throw new ServiceException(400, "Merchant with contract code does not exists");   
        }

        // create reserved account instance
        ReservedAccount reservedAccount = accountMapper.toReservedAccount(pl);
        if (reservedAccountRepository.existsByAccountReference(pl.getAccountReference())) {
            throw new ServiceException(400, "You can not reserve two accounts with the same reference");
        }
        // TODO: we could do some bvn and nin validation with probably third party services
        reservedAccountRepository.save(reservedAccount);
        List<Bank> banks = null;
        if (pl.isGetAllAvailableBanks()) {
            // get all available banks and get the top three
            List<Bank> queryBanks = bankRepository.findAll();
            // slice just three
            banks = queryBanks.subList(0, 3);
        } else {
            if (pl.getPreferredBanks().size() < 0) {
                throw new ServiceException(400, "Preferred banks list is empty");
            }
            List<String> preferredBanks = pl.getPreferredBanks();
            banks = preferredBanks.stream().map(item -> bankRepository.findByCode(item).orElseThrow(() -> new ServiceException(404, "Bank not available"))).collect(Collectors.toList());
        }
        // create new accounts with bank info
        List<Account> accounts =  banks.stream().map(bank -> createBankAccount(bank, reservedAccount)).collect(Collectors.toList());;
        // get the create reserved account
        ReservedAccount createdReservedAccount = reservedAccountRepository.findByAccountReference(pl.getAccountReference()).orElseThrow(() -> new ServiceException(406, "Couldn't Create Account"));
        createdReservedAccount.setAccounts(accounts);
        return createdReservedAccount;
    }

    public ReservedAccount getReservedAccountByAccountRef(String accountReference) {
        return reservedAccountRepository.findByAccountReference(accountReference).orElseThrow(() -> new ServiceException(404, "Reserved Not Found!"));
    }

    // create bank info
    private Account createBankAccount(Bank bank, ReservedAccount reservedAccount) {
        String bankAccountNumber = Long.toString(Utils.generateRandomNumber());
        Account bankAccount = Account.builder()
                                        .accountName(reservedAccount.getAccountName())
                                        .reservedAccount(reservedAccount)
                                        .accountName(reservedAccount.getAccountName())
                                        .accountNumber(bankAccountNumber)
                                        .bankCode(bank.getCode())
                                        .bankName(bank.getName())
                                        .build();

        return accountRepository.save(bankAccount);
    }

    

}
