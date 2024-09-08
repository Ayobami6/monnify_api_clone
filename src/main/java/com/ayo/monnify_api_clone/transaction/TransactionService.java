package com.ayo.monnify_api_clone.transaction;

import com.ayo.monnify_api_clone.exception.InternalServerException;
import com.ayo.monnify_api_clone.transaction.dto.*;
import com.ayo.monnify_api_clone.transaction.enums.Status;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ayo.monnify_api_clone.banks.Bank;
import com.ayo.monnify_api_clone.banks.BankRepository;
import com.ayo.monnify_api_clone.exception.ServiceException;
import com.ayo.monnify_api_clone.user.UserEntity;
import com.ayo.monnify_api_clone.user.UserRepository;
import com.ayo.monnify_api_clone.utils.Utils;

import lombok.AllArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    // dependencies
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final TransactionMapper transactionMapper;

    // initialize a transaction

    public InitTransactionResponseDto initTransaction(InitializeTransactionDto pl ) {
        // get merchant by conttractcode 
        UserEntity user = userRepository.findByContractCode(Long.parseLong(pl.getContractCode())).orElseThrow(() -> new ServiceException(404, "Merchant with the provided contract code not found"));

        // map payload data to entity
        Transaction transaction = Transaction.builder()
                                            .amount(pl.getAmount())
                                            .apiKey(user.getApiKey())
                                            .contractCode(pl.getContractCode())
                                            .currencyCode(pl.getCurrencyCode())
                                            .customerEmail(pl.getCustomerEmail())
                                            .customerName(pl.getCustomerName())
                                            .merchantName(user.getMerchantName())
                                            .paymentReference(pl.getPaymentReference())
                                            .paymentDescription(pl.getPaymentDescription())
                                            .redirectUrl(pl.getRedirectUrl())
                                            .paymentMethods(pl.getPaymentMethods())
                                            .fee("10")
                                            .build();
        
        // save transaction
        transactionRepository.save(transaction);


        InitTransactionResponseDto res = InitTransactionResponseDto.builder()
                                                            .transactionReference(transaction.getTransactionReference())
                                                            .apiKey(transaction.getApiKey())
                                                            .paymentReference(transaction.getPaymentReference())
                                                            .merchantName(transaction.getMerchantName())
                                                            .enabledPaymentMethod(transaction.getPaymentMethods())
                                                            .checkoutUrl(String.format("https://sandbox.sdk.monnify.com/checkout/%s", transaction.getTransactionReference()))
                                                            .build();

        return res;

    }

    // bank transfer init

    public BankTransferInitResponseDto initializeBankTransfer(BankTransferDto pl) {

        // get the bank info from bank table 
        Bank bank = bankRepository.findByCode(pl.getBankCode()).orElseThrow(() -> new ServiceException(404, "Bank not found"));
        // get the transaction info from transaction table
        Transaction transaction = transactionRepository.findByTransactionReference(pl.getTransactionReference()).orElseThrow(() -> new ServiceException(404, "Transaction not found"));
        // get dummy bank account number and account name
        String bankAccountNumber = Long.toString(Utils.generateRandomNumber());
        String bankAccountName = "Test Bank Account";
        LocalDateTime requestTime = LocalDateTime.now();
        LocalDateTime expiresOn = requestTime.plusMinutes(30);
        String ussdCode = String.format("*%s*%s#", bank.getUssdTemplate(), bankAccountNumber);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("accountNumber", bankAccountNumber);
        data.put("accountName", bankAccountName);
        data.put("requestTime", requestTime);
        data.put("expiresOn", expiresOn);
        data.put("bankName", bank.getName());
        data.put("bankCode", bank.getCode());
        data.put("transactionReference", transaction.getTransactionReference());
        data.put("amount", transaction.getAmount());
        data.put("fee", transaction.getFee());
        data.put("paymentReference", transaction.getPaymentReference());
        data.put("totalPayable", transaction.getAmount());
        data.put("collectionChannel", "API_NOTIFICATION");
        data.put("ussdPayment", ussdCode);
        // cache the data 
        redisTemplate.opsForValue().set(pl.getTransactionReference(), data, 30, TimeUnit.MINUTES);

        return transactionMapper.toBankTransferInitResponseDto(data);

    }

    public CardChargeResponseDto chargeCard(ChargeCardDto pl) {

        LocalDate cardDate = LocalDate.of(Integer.parseInt(pl.getCard().getExpiryMonth()), Integer.parseInt(pl.getCard().getExpiryYear()), 1);
        LocalDate today = LocalDate.now();
        boolean isExpress = isAmericanExpress((pl.getCard().getNumber()));
        boolean isValid = isLuhnsValid(pl.getCard().getNumber());
        if (cardDate.isBefore(today)) {
            throw new ServiceException(400, "Card expiration date is not valid");
        }
        if (isExpress) {
            if ((pl.getCard().getCvv()).length() != 4 ) {
                throw new ServiceException(400, "American Express card number is not valid");
            }
        } else {
            if ((pl.getCard().getCvv()).length() != 3 ) {
                throw new ServiceException(400, "Card number is not valid");
            }
        }
        if (!isValid) {
                throw  new ServiceException(400, "Card number is not valid");
        }
//        get Transaction and update payment status
        Transaction transaction = transactionRepository.findByTransactionReference(pl.getTransactionReference()).orElseThrow(() -> new ServiceException(404, "Transaction not found"));
        transaction.setPaymentStatus(Status.PAID);
        transactionRepository.save(transaction);

        return CardChargeResponseDto.builder()
                .transactionReference(transaction.getTransactionReference())
                .paymentReference(transaction.getPaymentReference())
                .status("SUCCESS")
                .message("Transaction Successfully")
                .authorizedAmount(Integer.parseInt(transaction.getAmount()))
                .build();


    }


    /**
     * Checks if the card is an American Express type.
     *
     * @param cardNumber the card number
     * @return true if the card is American Express, false otherwise
     */
    private boolean isAmericanExpress(String cardNumber) {
        return cardNumber.startsWith("37") || cardNumber.startsWith("34");
    }

    /**
     * Luhn's Algorithm to validate card number.
     *
     * @param cardNumber the card number
     * @return true if the card number is valid, false otherwise
     */
    private boolean isLuhnsValid(String cardNumber) {
        try {
            if (cardNumber.length() < 16 || cardNumber.length() > 19) {
                return false;
            }

            int[] digits = new int[cardNumber.length()];
            for (int i = 0; i < cardNumber.length(); i++) {
                digits[i] = Character.getNumericValue(cardNumber.charAt(cardNumber.length() - 1 - i));
            }

            int totalSum = 0;
            for (int i = 0; i < digits.length; i++) {
                int digit = digits[i];
                if (i % 2 == 1) {
                    digit *= 2;
                    if (digit > 9) {
                        digit -= 9;
                    }
                }
                totalSum += digit;
            }

            return totalSum % 10 == 0;

        } catch (Exception e) {
            throw  new InternalServerException();
        }
    }



}