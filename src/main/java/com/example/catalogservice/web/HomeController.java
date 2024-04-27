package com.example.catalogservice.web;

import com.example.catalogservice.config.BookShopProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final BookShopProperties bookShopProperties;

    @GetMapping("/")
    public String home() {
        return bookShopProperties.getGreeting();
    }
}
