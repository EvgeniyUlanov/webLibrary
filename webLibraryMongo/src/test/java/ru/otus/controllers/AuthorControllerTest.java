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
import ru.otus.domain.Author;
import ru.otus.services.AuthorService;

@RunWith(SpringRunner.class)
@WebFluxTest(AuthorController.class)
@DisplayName("author controller test")
class AuthorControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("test get all authors")
    void testGetAll() {
        webClient
                .get().uri("/author/getAll")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("test add author")
    void testAddAuthor() {
        webClient
                .post().uri("/author/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(new Author("test")))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testFindByName() {
        webClient
                .get().uri("/author/findByName?authorName={name}", "test")
                .exchange()
                .expectStatus().isOk();
    }
}