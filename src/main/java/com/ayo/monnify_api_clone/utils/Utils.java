package com.ayo.monnify_api_clone.utils;

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

}
