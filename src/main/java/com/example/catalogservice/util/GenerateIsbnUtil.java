package com.example.catalogservice.util;

import org.springframework.stereotype.Component;

@Component
public class GenerateIsbnUtil {

    public static String generateUUID() {
        StringBuilder isbn = new StringBuilder("978");
        for (int i = 0; i < 9; i++) {
            isbn.append((int) (Math.random() * 10));
        }

        int sum = 0;
        for (int i = 0; i < isbn.length(); i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checksum = 10 - (sum % 10);
        if (checksum == 10) {
            checksum = 0;
        }

        isbn.append(checksum);

        return isbn.toString();
    }
}

