package com.example.catalogservice.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    Page<Book> findAll(Pageable pageable);

    boolean existsByIsbn(String isbn);

    void deleteById(long id);
}