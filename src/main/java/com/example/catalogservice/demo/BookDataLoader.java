package com.example.catalogservice.demo;

import com.example.catalogservice.domain.Book;
import com.example.catalogservice.domain.BookRepository;
import com.example.catalogservice.util.GenerateIsbnUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Profile("testdata")
@RequiredArgsConstructor
public class BookDataLoader {

    private final BookRepository bookRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void loadBookTestData() {
        bookRepository.deleteAll();
        Book book1 = new Book(GenerateIsbnUtil.generateUUID(), "Northern Lights",
                "Lyra Silverstar", 9.90, null, new Date().toInstant(), new Date().toInstant(), 0);
        Book book2 = new Book(GenerateIsbnUtil.generateUUID(), "Polar Journey",
                "Iorek Polarson", 12.90, null, new Date().toInstant(), new Date().toInstant(), 0);
        bookRepository.saveAll(List.of(book1, book2));
    }
}