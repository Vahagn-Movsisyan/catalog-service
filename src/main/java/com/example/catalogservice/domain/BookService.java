package com.example.catalogservice.domain;

import com.example.catalogservice.util.GenerateIsbnUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Page<Book> getAllBooks() {
        return bookRepository.findAll(PageRequest.of(0, 10).withSort(Sort.by("createdDate")));
    }

    public Book findById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.valueOf(id)));
    }

    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book) {
        book.setIsbn(GenerateIsbnUtil.generateUUID());
        book.setCreatedDate(new Date().toInstant());
        book.setLastModifiedDate(new Date().toInstant());
        book.setVersion(1);
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(long id) {
        bookRepository.deleteById(id);
    }

    public Book editBookDetails(long id, Book book) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setIsbn(book.getIsbn());
                    existingBook.setTitle(book.getTitle());
                    existingBook.setAuthor(book.getAuthor());
                    existingBook.setPrice(book.getPrice());
                    existingBook.setPublisher(book.getPublisher());
                    return bookRepository.save(existingBook);
                })
                .orElseThrow(() -> new BookNotFoundException(String.valueOf(id)));
    }
}