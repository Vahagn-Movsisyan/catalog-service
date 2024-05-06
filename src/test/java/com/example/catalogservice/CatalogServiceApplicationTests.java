package com.example.catalogservice;

import com.example.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenGetRequestWithIdThenBookReturned() {
        Book bookToCreate = new Book("1231231230", "Title", "Author", 9.90, null);
        Book expectedBook = webTestClient
                .post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(book -> assertThat(book).isNotNull())
                .returnResult().getResponseBody();

        webTestClient
                .get()
                .uri("/books/" + "1231231230")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.getId()).isEqualTo(expectedBook.getIsbn());
                });
    }

    @Test
    void whenPostRequestThenBookCreated() {
        Book expectedBook = new Book("1231231230", "Title", "Author", 9.90, null);

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.getIsbn()).isEqualTo(expectedBook.getIsbn());
                });
    }

    @Test
    void whenPutRequestThenBookUpdated() {
        Book bookToCreate = new Book("1231231230", "Title", "Author", 9.90, null);
        Book createdBook = webTestClient
                .post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(book -> assertThat(book).isNotNull())
                .returnResult().getResponseBody();
        Book bookToUpdate = new Book(createdBook.getIsbn(), createdBook.getTitle(), createdBook.getAuthor(), 7.95, null);

        webTestClient
                .put()
                .uri("/books/" + "1231231232")
                .bodyValue(bookToUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.getPrice()).isEqualTo(bookToUpdate.getPrice());
                });
    }

    @Test
    void whenDeleteRequestThenBookDeleted() {

        Book bookToCreate = new Book("1231231230", "Title", "Author", 9.90, null);
        webTestClient
                .post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated();

        webTestClient
                .delete()
                .uri("/books/" + "1231231233")
                .exchange()
                .expectStatus().isNoContent();

        webTestClient
                .get()
                .uri("/books/" + "1231231233")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class).value(errorMessage ->
                        assertThat(errorMessage).isEqualTo("The book with ISBN " + "1231231233" + " was not found.")
                );
    }
}