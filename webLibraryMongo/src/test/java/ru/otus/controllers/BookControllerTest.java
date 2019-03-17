package ru.otus.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookContainerDto;
import ru.otus.dto.CommentContainerDto;
import ru.otus.services.BookService;


@RunWith(SpringRunner.class)
@WebFluxTest(BookController.class)
@DisplayName("book controller test")
class BookControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("when book/all than status ok")
    void testGetAll() {
        webClient
                .get().uri("/book/all")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book/add than status ok")
    void testAddBook() {
        webClient
                .post().uri("/book/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(new Book(new Genre("genre"), "test")))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book/delete than status ok")
    void testDeleteBook() {
        webClient
                .delete().uri("/book/delete/{id}", 1)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book/info than status ok")
    void testGetBookInfo() {
        webClient
                .get().uri("/book/info?book_id={id}", 1)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book/addComment than status ok")
    void testAddCommentToBook() {
        webClient
                .post().uri("/book/addComment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(new CommentContainerDto()))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book/addAuthorToBook than status ok")
    void testAddAuthorToBook() {
        webClient
                .post().uri("/book/addAuthorToBook")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(new BookContainerDto()))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book/findBuAuthor than status ok")
    void testFindByAuthor() {
        webClient
                .get().uri("/book/findByAuthor?authorName={name}", "test")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book/findByGenre than status ok")
    void testFindByGenre() {
        webClient
                .get().uri("/book/findByGenre?genreName={name}", "test")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("when book/findByName than status ok")
    void testFindByName() {
        webClient
                .get().uri("/book/findByName?bookName={name}", "test")
                .exchange()
                .expectStatus().isOk();
    }
}