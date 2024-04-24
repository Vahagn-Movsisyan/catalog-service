package com.example.catalogservice.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IsbnGenerator {
    public static String generateIsbn() {
        return UUID.randomUUID().toString();
    }
}
