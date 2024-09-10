package com.ayo.monnify_api_clone.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class Utils {

    public static Long generateRandomNumber() {
        UUID uuid = UUID.randomUUID();
        long uniqueRandomNumber = (uuid.hashCode() & 0xFFFFFFFFL) % 10000000000L;
        return uniqueRandomNumber;
    }

    public static String generateRandomOTP() {
        Random random = new Random(System.nanoTime());
        int randomNumber = random.nextInt(9000) + 1000;
        return String.valueOf(randomNumber);
    }

    public static int randomNumber(int minValue, int maxValue) {
        Random random = new Random();
        int randomNumber = random.nextInt(maxValue - minValue + 1) + minValue;
        return randomNumber;
    };

    public static String generateTransactionRef() {
        String prefix = "MNFYC";

        LocalDateTime now = LocalDateTime.now();

        int leading = randomNumber(1, 99);
        int suffix = randomNumber(1, 9999);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = now.format(formatter);

        String code = String.format("%06d", suffix);

        String ref = String.format("%s|%s|%s|%s", prefix, leading, timestamp, code);
        return ref;
    }

    public static String generateSubAccountCode() {
        String prefix = "MFYC_SUB";
        Long suffix = generateRandomNumber();
        String ref = String.format("%s_%s", prefix, suffix);
        return ref;

    }

}
