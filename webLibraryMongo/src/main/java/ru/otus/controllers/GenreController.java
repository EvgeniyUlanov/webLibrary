package ru.otus.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Genre;
import ru.otus.services.GenreService;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RestController
public class GenreController {

    private GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(value = "/genre/getAll")
    public Flux<Genre> getAllGenres() {
        return genreService.getAll();
    }

    @PostMapping(value = "/genre/add", consumes = "application/json")
    public Mono<ServerResponse> addGenre(@RequestBody Genre genre) {
        return genreService.createNewGenre(genre).then(ok().build());
    }
}