package com.example.catalogservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "title can not be null")
    @Size(max = 50, message = "Profession length must be less than or equal to 100 characters")
    private String title;

    @NotBlank(message = "author can not be null")
    @Size(max = 50, message = "Profession length must be less than or equal to 100 characters")
    private String author;

    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0")
    @Min(value = 0, message = "Expected salary must be positive")
    @Digits(integer = 10, fraction = 0, message = "Expected salary must be numeric")
    private Double price;

    @Size(max = 50, message = "Profession length must be less than or equal to 100 characters")
    private String publisher;
}
