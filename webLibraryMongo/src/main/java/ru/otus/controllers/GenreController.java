package ru.otus.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Genre;
import ru.otus.services.GenreService;

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
    public Mono<HttpStatus> addGenre(@RequestBody Genre genre) {
        return genreService
                .createNewGenre(genre)
                .thenReturn(HttpStatus.OK);
    }
}