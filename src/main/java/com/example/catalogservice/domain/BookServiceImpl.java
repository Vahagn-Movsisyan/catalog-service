package com.example.catalogservice.domain;

import com.example.catalogservice.util.IsbnGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final int PAGE_SIZE = 5;

    @Override
    public Page<Book> findAll(int index) {
        return bookRepository.findAll(PageRequest.of(index, PAGE_SIZE).withSort(Sort.by("id")));
    }

    @Override
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));    }

    @Override
    public Book save(Book book) {
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent())
            throw new BookAlreadyExistException("Book already exists");

        book.setIsbn(IsbnGenerator.generateIsbn());
        return bookRepository.save(book);
    }

    @Override
    public Book updateById(long bookId, Book book) {
        Optional<Book> byId = bookRepository.findById(bookId);
        if (byId.isPresent()) {
            Book existingBook = byId.get();

            if (!book.equals(byId.get())) {

                existingBook.setTitle(book.getTitle());
                existingBook.setAuthor(book.getAuthor());
                existingBook.setPrice(book.getPrice());
                existingBook.setPublisher(book.getPublisher());

                bookRepository.save(byId.get());
                return byId.get();
            }
            return book;
        }
        return null;
    }

    @Override
    public Book findById(long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
