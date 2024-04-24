package com.example.catalogservice.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testFindAll() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        Page<Book> page = new PageImpl<>(books);
        when(bookRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Book> result = bookService.findAll(0);

        verify(bookRepository).findAll(PageRequest.of(0, 5).withSort(Sort.by("id")));

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void testFindByIsbn() {
        Book book = new Book();
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(book));

        Book result = bookService.findByIsbn("test-isbn");

        verify(bookRepository).findByIsbn("test-isbn");

        assertNotNull(result);
    }

    @Test
    public void testFindByIsbn_NotFound() {
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.findByIsbn("non-existent-isbn"));
    }

    @Test
    public void testSave() {
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.empty());
        when(bookRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Book book = new Book();
        book.setTitle("Test Book");
        book.setIsbn("test-isbn");

        Book savedBook = bookService.save(book);

        verify(bookRepository).findByIsbn("test-isbn");
        verify(bookRepository).save(book);

        assertNotNull(savedBook);
        assertEquals("Test Book", savedBook.getTitle());
    }

    @Test
    public void testSave_IsbnAlreadyExists() {
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(new Book()));

        Book book = new Book();
        book.setTitle("Test Book");
        book.setIsbn("test-isbn");

        assertThrows(BookAlreadyExistException.class, () -> bookService.save(book));
    }

    @Test
    public void testUpdateById() {
        long bookId = 1L;
        Book existingBook = new Book();
        existingBook.setId(bookId);
        existingBook.setTitle("Existing Book");
        existingBook.setIsbn("existing-isbn");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Book");

        Book result = bookService.updateById(bookId, updatedBook);

        verify(bookRepository).findById(bookId);
        verify(bookRepository).save(existingBook);

        assertNotNull(result);
        assertEquals("Updated Book", result.getTitle());
    }


    @Test
    public void testFindById() {
        long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Book result = bookService.findById(bookId);

        verify(bookRepository).findById(bookId);

        assertNotNull(result);
        assertEquals(bookId, result.getId());
    }
}