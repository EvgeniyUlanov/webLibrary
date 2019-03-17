package ru.otus.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.otus.domain.Genre;
import ru.otus.services.GenreService;

@RunWith(SpringRunner.class)
@WebFluxTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private GenreService genreService;

    @Test
    void getAllGenres() {
        webClient
                .get().uri("/genre/getAll")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void addGenre() {
        webClient
                .post().uri("/genre/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(new Genre()))
                .exchange()
                .expectStatus().isOk();
    }
}