package com.example.catalogservice.domain;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void whenBookToCreateAlreadyExistsThenThrows() {
        var bookIsbn = "1234561232";
        Book bookToCreate = new Book(bookIsbn, "Title", "Author", 9.90, null);
        when(bookRepository.existsByIsbn(bookIsbn)).thenReturn(true);
        assertThatThrownBy(() -> bookService.addBookToCatalog(bookToCreate))
                .isInstanceOf(BookAlreadyExistException.class)
                .hasMessage("A book with ISBN " + bookIsbn + " already exists.");
    }

    @Test
    void whenBookToReadDoesNotExistThenThrows() {
        var bookIsbn = "1234561232";
        when(bookRepository.findByIsbn(bookIsbn)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.findByIsbn(bookIsbn))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("The book with ISBN " + bookIsbn + " was not found.");
    }
}