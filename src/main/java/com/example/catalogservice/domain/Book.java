package com.example.catalogservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import jakarta.persistence.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.Instant;

@Entity
@Table
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The book ISBN must be defined.")
    @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.")
    private String isbn;

    @NotBlank(message = "The book title must be defined.")
    private String title;

    @NotBlank(message = "The book author must be defined.")
    private String author;

    @NotNull(message = "The book price must be defined.")
    @Positive(message = "The book price must be greater than zero.")
    private Double price;

    private String publisher;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    @Version
    private int version;

    public Book() {}

    public Book(String isbn, String title, String author, Double price, String publisher, Instant createdDate, Instant lastModifiedDate, int version) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.publisher = publisher;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.version = version;
    }

    public Book(String isbn, String title, String author, Double price, String publisher) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.publisher = publisher;
    }
}
