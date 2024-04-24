package com.example.catalogservice.web;

import com.example.catalogservice.domain.Book;
import com.example.catalogservice.domain.BookNotFoundException;
import com.example.catalogservice.domain.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/list")
    public ResponseEntity<Page<Book>> list() {
        return ResponseEntity.ok(bookService.findAll(0));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") long id) {
        try {
            Book book = bookService.findById(id);
            return ResponseEntity.ok().body(book);
        } catch (BookNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @Valid @RequestBody Book book) {
        try {
            Book updatedBook = bookService.updateById(id, book);
            return ResponseEntity.ok().body(updatedBook);
        } catch (BookNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
