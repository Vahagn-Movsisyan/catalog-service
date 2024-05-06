package com.example.catalogservice;

import com.example.catalogservice.domain.Book;
import com.example.catalogservice.domain.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public Page<Book> getAll() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable long id) {
        Book book = bookService.findById(id);
        if (book != null) {
            return ResponseEntity.ok(bookService.findById(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> post(@Valid @RequestBody Book book) {
        if (book != null) {
            return ResponseEntity.ok(bookService.addBookToCatalog(book));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> put(@PathVariable long id, @Valid @RequestBody Book book) {
        if (book != null && id != 0) {
            return ResponseEntity.ok(bookService.editBookDetails(id, book));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        bookService.removeBookFromCatalog(id);
    }
}
