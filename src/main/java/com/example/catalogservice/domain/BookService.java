package com.example.catalogservice.domain;

import org.springframework.data.domain.Page;

public interface BookService {

    Page<Book> findAll(int index);

    Book findByIsbn(String isbn);

    Book save(Book book);

    Book updateById(long bookId, Book book);

    Book findById(long bookId);

    boolean existsByIsbn(String isbn);

    void deleteById(long id);
}
