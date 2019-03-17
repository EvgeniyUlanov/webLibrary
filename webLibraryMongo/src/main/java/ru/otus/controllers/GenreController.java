package ru.otus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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
    public ResponseEntity<String> addGenre(@RequestBody Genre genre) {
        genreService.createNewGenre(genre);
        return ResponseEntity.ok("{}");
    }
}