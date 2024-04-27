package com.example.catalogservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "book")
@Data
@EqualsAndHashCode

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String isbn;

    @NotBlank(message = "Title can not be null")
    @Size(max = 50, message = "Title length must be less than or equal to 50 characters")
    private String title;

    @NotBlank(message = "Author can not be null")
    @Size(max = 50, message = "Author length must be less than or equal to 50 characters")
    private String author;

    private Double price;

    @Size(max = 50, message = "Publisher length must be less than or equal to 50 characters")
    private String publisher;
}
