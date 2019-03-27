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
import reactor.core.publisher.Mono;
import ru.otus.domain.Genre;
import ru.otus.services.GenreService;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(GenreController.class)
@DisplayName("genre controller test")
class GenreControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("test when genre/getAll that status is ok")
    void testGetAllGenres() {
        webClient
                .get().uri("/genre")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("test when genre/add that status is ok")
    void testAddGenre() {
        Genre genre = new Genre("test");
        when(genreService.createNewGenre(genre)).thenReturn(Mono.just(genre));

        webClient
                .post().uri("/genre")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(genre))
                .exchange()
                .expectStatus().isOk();
    }
}