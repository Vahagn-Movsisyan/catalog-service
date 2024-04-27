package com.example.catalogservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "bookshop")
public class BookShopProperties {
    private String greeting;
}
