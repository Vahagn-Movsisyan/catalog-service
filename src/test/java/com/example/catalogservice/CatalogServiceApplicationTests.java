package com.example.catalogservice;

import com.example.catalogservice.domain.Book;
import com.example.catalogservice.util.GenerateIsbnUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenGetRequestWithIdThenBookReturned() {
        String isbn = GenerateIsbnUtil.generateUUID();
        Book bookToCreate = new Book(isbn, "Title", "Author", 9.90, null);
        Book expectedBook = webTestClient
                .post()
                .uri("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        webTestClient
                .get()
                .uri("/books/" + isbn)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .value(actualBook -> assertThat(actualBook).isEqualTo(expectedBook));
    }

    @Test
    void whenPostRequestThenBookCreated() {
        String isbn = GenerateIsbnUtil.generateUUID();
        Book expectedBook = new Book(isbn, "Title", "Author", 9.90, null);
        webTestClient
                .post()
                .uri("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .value(actualBook -> assertThat(actualBook).isEqualTo(expectedBook));
    }

    @Test
    void whenPutRequestThenBookUpdated() {
        String isbn = GenerateIsbnUtil.generateUUID();
        Book bookToCreate = new Book(isbn, "Title", "Author", 9.90, null);
        Book createdBook = webTestClient
                .post()
                .uri("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();
        Book bookToUpdate = new Book(createdBook.getIsbn(), "New Title", "New Author", 7.95, null);

        webTestClient
                .put()
                .uri("/books/" + isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookToUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .value(actualBook -> assertThat(actualBook).isEqualTo(bookToUpdate));
    }

    @Test
    void whenDeleteRequestThenBookDeleted() {
        String isbn = GenerateIsbnUtil.generateUUID();
        Book bookToCreate = new Book(isbn, "Title", "Author", 9.90, null);
        webTestClient
                .post()
                .uri("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated();

        webTestClient
                .delete()
                .uri("/books/" + isbn)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient
                .get()
                .uri("/books/" + isbn)
                .exchange()
                .expectStatus().isNotFound();
    }
}
